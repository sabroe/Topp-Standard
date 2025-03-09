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

package com.yelstream.topp.standard.interop.script.rhino;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Java script-engine wrapping of the Mozilla Rhino JavaScript engine.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-07
 */
@AllArgsConstructor
@SuppressWarnings("LombokGetterMayBeUsed")
public class RhinoScriptEngine extends AbstractScriptEngine {

    @Getter
    private final RhinoScriptEngineFactory factory;

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return eval(new StringReader(script), context);
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        try (Context cx = Context.enter();
             StringWriter writer = new StringWriter()) {
            Scriptable scope = cx.initStandardObjects();
            char[] buffer = new char[1024];
            int read;

            while ((read=reader.read(buffer))!=-1) {
                writer.write(buffer,0,read);
            }

            return cx.evaluateString(scope,writer.toString(),"<script>",1,null);
        } catch (IOException ex) {
            ScriptException exception=new ScriptException(String.format("Failure to evaluate script; exception message is %s!",ex.getMessage()));
            exception.initCause(ex);
            throw exception;
        } catch (Exception ex) {
            throw new ScriptException(ex);
        } finally {
            Context.exit();
        }
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }
}
