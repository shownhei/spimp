/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.util;

/**
 * PDU消息类。
 * 
 * @author David Niu(david.kosoon@gmail.com)
 */
public class PDU {
	private String pudContents;
	private int pduLength;

	public int getPduLength() {
		return pduLength;
	}

	public String getPudContents() {
		return pudContents;
	}

	public void setPduLength(int pduLength) {
		this.pduLength = pduLength;
	}

	public void setPudContents(String pudContents) {
		this.pudContents = pudContents;
	}
}
