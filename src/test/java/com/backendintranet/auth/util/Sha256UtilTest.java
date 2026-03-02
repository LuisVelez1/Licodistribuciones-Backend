package com.backendintranet.auth.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Sha256UtilTest {

    @Test
    void shouldHashUsingSha256() {
        String hash = Sha256Util.hash("Admin123*");
        assertEquals("a58b12d06fb4ff79f25f8c47f4ac6ac0b19dce26e2eb2710f7dc8b2f7c9f6283", hash);
    }
}
