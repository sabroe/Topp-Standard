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

package com.yelstream.topp.standard.security.cert;

import lombok.experimental.UtilityClass;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility addressing {@link java.security.cert.X509Certificate} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-04-04
 */
@UtilityClass
public class X509Certificates {
    //TO-DO: Initially empty!


    public static String prettyPrintCertificate(X509Certificate c) throws CertificateParsingException {
        String san = c.getSubjectAlternativeNames() != null
                ? c.getSubjectAlternativeNames().stream()
                .map(name -> (String) name.get(1)) // Extract the value (e.g., DNS name)
                .collect(Collectors.joining(", "))
                : "None";

        return String.format("""
        Certificate for: %s
        Issued by: %s
        Valid from: %s to %s
        Subject Alternative Names: %s
        Public Key: %s
        Serial Number: %s
        Signature Algorithm: %s""",
                c.getSubjectX500Principal(),
                c.getIssuerX500Principal(),
                c.getNotBefore(),
                c.getNotAfter(),
                san,
                c.getPublicKey(),
                c.getSerialNumber(),
                c.getSigAlgName()
        );
    }


    public static String prettyPrintCertificate2(X509Certificate c) throws Exception {
        X509CertificateHolder certHolder = new X509CertificateHolder(c.getEncoded());
        Extension sanExt = certHolder.getExtension(Extension.subjectAlternativeName);
        Extension keyUsageExt = certHolder.getExtension(Extension.keyUsage);
        Extension extKeyUsageExt = certHolder.getExtension(Extension.extendedKeyUsage);
        Extension crlDistExt = certHolder.getExtension(Extension.cRLDistributionPoints);

        return String.format("""
            Certificate for: %s
            Issued by: %s
            Valid from: %s to %s
            Subject Alternative Names: %s
            Public Key: %s
            Key Usage: %s
            Extended Key Usage: %s
            Serial Number: %s
            Signature Algorithm: %s
            CRL Distribution Points: %s""",
                certHolder.getSubject(),
                certHolder.getIssuer(),
                certHolder.getNotBefore(),
                certHolder.getNotAfter(),
                sanExt != null ? sanExt.getParsedValue().toString() : "None",
                certHolder.getSubjectPublicKeyInfo(),
                formatKeyUsage(keyUsageExt),
                extKeyUsageExt != null ? extKeyUsageExt.getParsedValue().toString() : "None",
                certHolder.getSerialNumber(),
                certHolder.getSignatureAlgorithm().getAlgorithm(),
                crlDistExt != null ? crlDistExt.getParsedValue().toString() : "None"
        );
    }

    private static String formatKeyUsage(Extension keyUsageExt) {
        if (keyUsageExt == null) return "None";
        KeyUsage usage = KeyUsage.getInstance(keyUsageExt.getParsedValue());
        if (usage == null) return "None";
        return Arrays.stream(new String[]{
                usage.hasUsages(KeyUsage.digitalSignature) ? "digitalSignature" : null,
                usage.hasUsages(KeyUsage.nonRepudiation) ? "nonRepudiation" : null,
                usage.hasUsages(KeyUsage.keyEncipherment) ? "keyEncipherment" : null,
                usage.hasUsages(KeyUsage.dataEncipherment) ? "dataEncipherment" : null,
                usage.hasUsages(KeyUsage.keyAgreement) ? "keyAgreement" : null,
                usage.hasUsages(KeyUsage.keyCertSign) ? "keyCertSign" : null,
                usage.hasUsages(KeyUsage.cRLSign) ? "cRLSign" : null,
                usage.hasUsages(KeyUsage.encipherOnly) ? "encipherOnly" : null,
                usage.hasUsages(KeyUsage.decipherOnly) ? "decipherOnly" : null
        }).filter(Objects::nonNull).collect(Collectors.joining(", "));
    }


    public static String prettyPrintCertificate3(X509Certificate c) throws CertificateParsingException {
        String san = c.getSubjectAlternativeNames() != null
                ? c.getSubjectAlternativeNames().stream()
                .map(name -> (String) name.get(1))
                .collect(Collectors.joining(", "))
                : "None";
        String keyUsage = formatKeyUsage(c.getKeyUsage());

        return String.format("""
        Certificate for: %s
        Issued by: %s
        Valid from: %s to %s
        Subject Alternative Names: %s
        Public Key: %s
        Key Usage: %s
        Extended Key Usage: %s
        Basic Constraints: %s
        Serial Number: %s
        Signature Algorithm: %s""",
                c.getSubjectX500Principal(),
                c.getIssuerX500Principal(),
                c.getNotBefore(),
                c.getNotAfter(),
                san,
                c.getPublicKey(),
                keyUsage,
                c.getExtendedKeyUsage() != null ? String.join(", ", c.getExtendedKeyUsage()) : "None",
                c.getBasicConstraints() >= 0 ? "CA (PathLen: " + c.getBasicConstraints() + ")" : "Not a CA",
                c.getSerialNumber(),
                c.getSigAlgName()
        );
    }

    private static String formatKeyUsage(boolean[] usage) {
        if (usage == null) return "None";
        String[] names = {"digitalSignature", "nonRepudiation", "keyEncipherment", "dataEncipherment",
                "keyAgreement", "keyCertSign", "cRLSign", "encipherOnly", "decipherOnly"};
        return IntStream.range(0, Math.min(usage.length, names.length))
                .filter(i -> usage[i])
                .mapToObj(i -> names[i])
                .collect(Collectors.joining(", "));
    }
}
