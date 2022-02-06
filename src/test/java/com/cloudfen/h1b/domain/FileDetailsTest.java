package com.cloudfen.h1b.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudfen.h1b.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileDetails.class);
        FileDetails fileDetails1 = new FileDetails();
        fileDetails1.setId(1L);
        FileDetails fileDetails2 = new FileDetails();
        fileDetails2.setId(fileDetails1.getId());
        assertThat(fileDetails1).isEqualTo(fileDetails2);
        fileDetails2.setId(2L);
        assertThat(fileDetails1).isNotEqualTo(fileDetails2);
        fileDetails1.setId(null);
        assertThat(fileDetails1).isNotEqualTo(fileDetails2);
    }
}
