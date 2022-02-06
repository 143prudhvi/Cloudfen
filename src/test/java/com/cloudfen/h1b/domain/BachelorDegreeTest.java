package com.cloudfen.h1b.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudfen.h1b.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BachelorDegreeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BachelorDegree.class);
        BachelorDegree bachelorDegree1 = new BachelorDegree();
        bachelorDegree1.setId(1L);
        BachelorDegree bachelorDegree2 = new BachelorDegree();
        bachelorDegree2.setId(bachelorDegree1.getId());
        assertThat(bachelorDegree1).isEqualTo(bachelorDegree2);
        bachelorDegree2.setId(2L);
        assertThat(bachelorDegree1).isNotEqualTo(bachelorDegree2);
        bachelorDegree1.setId(null);
        assertThat(bachelorDegree1).isNotEqualTo(bachelorDegree2);
    }
}
