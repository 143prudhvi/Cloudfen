package com.cloudfen.h1b.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudfen.h1b.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class H1BTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(H1B.class);
        H1B h1B1 = new H1B();
        h1B1.setId(1L);
        H1B h1B2 = new H1B();
        h1B2.setId(h1B1.getId());
        assertThat(h1B1).isEqualTo(h1B2);
        h1B2.setId(2L);
        assertThat(h1B1).isNotEqualTo(h1B2);
        h1B1.setId(null);
        assertThat(h1B1).isNotEqualTo(h1B2);
    }
}
