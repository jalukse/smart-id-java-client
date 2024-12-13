package ee.sk.smartid;

/*-
 * #%L
 * Smart ID sample Java client
 * %%
 * Copyright (C) 2018 - 2024 SK ID Solutions AS
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class AuthenticationIdentityMapperTest {

    private static final byte[] AUTH_CERT = FileUtil.readFileBytes("test-certs/auth-cert-40504040001.pem.crt");

    @Test
    void from() {
        X509Certificate certificate = getX509Certificate();
        AuthenticationIdentity authenticationIdentity = AuthenticationIdentityMapper.from(certificate);

        assertEquals("OK", authenticationIdentity.getGivenName());
        assertEquals("TESTNUMBER", authenticationIdentity.getSurname());
        assertEquals("40504040001", authenticationIdentity.getIdentityNumber());
        assertEquals("EE", authenticationIdentity.getCountry());

        assertEquals(certificate, authenticationIdentity.getAuthCertificate());
        assertEquals(Optional.of(LocalDate.of(1905, 4, 4)), authenticationIdentity.getDateOfBirth());
    }

    private static X509Certificate getX509Certificate() {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(AUTH_CERT));
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }
}
