package com.brazoft.foundation.io;

import java.io.InputStream;

/**
 * @author Anderson Braz - anderson.braz@brazoft.com.br
 * 
 */
public class StringInputStream
    extends InputStream {

    /**
     * @param value
     */
    public StringInputStream(String value) {
	this.index = 0;
	this.value = value;
    }

    public int available() {
	return this.value.length() - this.index;
    }

    public int read() {
	if (this.index == this.value.length()) {
	    return -1;
	}

	return this.value.charAt(this.index++);
    }

    public int read(byte buf[]) {
	return read(buf, 0, buf.length);
    }

    public int read(byte buf[], int offset, int len) {
	if (this.index == this.value.length()) {
	    return -1;
	}

	len = Math.min(len, available());
	for (int i = 0; i < len; i++) {
	    buf[i + offset] = (byte)this.value.charAt(this.index++);
	}

	return len;
    }

    private final String value;

    private int          index;
}