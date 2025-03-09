/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.interop.script;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Script-engine manager with extended capabilities.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-08
 */
public class ExtendedScriptEngineManager extends ScriptEngineManager {
    /**
     * Loader used by the default constructor of 'ScriptEngineManager'.
     */
    public static final ClassLoader DEFAULT_CLASSLOADER=Thread.currentThread().getContextClassLoader();

    @SuppressWarnings({"java:S1068","FieldCanBeLocal"})  //Nope, necessary for Lombok 'toBuilder'!
    private final ClassLoader loader;

    private final Function<ScriptEngineManager,List<ScriptEngineFactory>> engineFactoriesOp;

    private final BiFunction<ScriptEngineManager,String,ScriptEngine> engineByNameOp;

    private final BiFunction<ScriptEngineManager,String,ScriptEngine> engineByExtensionOp;

    private final BiFunction<ScriptEngineManager,String,ScriptEngine> engineByMimeTypeOp;

    @SuppressWarnings({"java:S1068","FieldCanBeLocal"})  //Nope, necessary for Lombok 'toBuilder'!
    private final Bindings bindings;

    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    protected ExtendedScriptEngineManager(ClassLoader loader,
                                          Function<ScriptEngineManager,List<ScriptEngineFactory>> engineFactoriesOp,
                                          BiFunction<ScriptEngineManager,String,ScriptEngine> engineByNameOp,
                                          BiFunction<ScriptEngineManager,String,ScriptEngine> engineByExtensionOp,
                                          BiFunction<ScriptEngineManager,String,ScriptEngine> engineByMimeTypeOp,
                                          Bindings bindings) {
        super(loader==null?DEFAULT_CLASSLOADER:loader);
        this.loader=loader;
        this.engineFactoriesOp=engineFactoriesOp;
        this.engineByNameOp=engineByNameOp;
        this.engineByExtensionOp=engineByExtensionOp;
        this.engineByMimeTypeOp=engineByMimeTypeOp;
        this.bindings=bindings;
        if (bindings!=null) {
            this.setBindings(bindings);
        }
    }

    @Override
    public List<ScriptEngineFactory> getEngineFactories() {
        if (engineFactoriesOp==null) {
            return super.getEngineFactories();
        } else {
            return engineFactoriesOp.apply(this);
        }
    }

    @Override
    public ScriptEngine getEngineByName(String shortName) {
        if (engineByNameOp ==null) {
            return super.getEngineByName(shortName);
        } else {
            return engineByNameOp.apply(this,shortName);
        }
    }

    @Override
    public ScriptEngine getEngineByExtension(String extension) {
        if (engineByExtensionOp==null) {
            return super.getEngineByExtension(extension);
        } else {
            return engineByExtensionOp.apply(this,extension);
        }
    }

    @Override
    public ScriptEngine getEngineByMimeType(String mimeType) {
        if (engineByMimeTypeOp==null) {
            return super.getEngineByMimeType(mimeType);
        } else {
            return engineByMimeTypeOp.apply(this,mimeType);
        }
    }

    public static class Builder {
        public Builder exact(List<ScriptEngineFactory> factories) {
            return this
                .engineFactoriesOp(m->factories)
                .engineByNameOp((manager,engineName)->factories.stream().filter(factory->engineName.equals(factory.getEngineName())).findFirst().map(ScriptEngineFactory::getScriptEngine).orElse(null))
                .engineByExtensionOp((manager,extension)->factories.stream().filter(factory->factory.getExtensions().contains(extension)).map(ScriptEngineFactory::getScriptEngine).findFirst().orElse(null))
                .engineByMimeTypeOp((manager,mimeType)->factories.stream().filter(factory->factory.getMimeTypes().contains(mimeType)).map(ScriptEngineFactory::getScriptEngine).findFirst().orElse(null));
        }
    }

    public static ExtendedScriptEngineManager of() {
        return builder().build();
    }

    public static ExtendedScriptEngineManager of(ClassLoader loader) {
        return builder().loader(loader).build();
    }

    public static ExtendedScriptEngineManager of(ClassLoader loader,
                                                 List<ScriptEngineFactory> engineFactories) {  //TO-DO: Consider the existence of this!
        return builder().loader(loader).exact(engineFactories).build();
    }
}
