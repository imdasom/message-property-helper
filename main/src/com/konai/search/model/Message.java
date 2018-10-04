package com.konai.search.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {
	public String value;
	public String[] tokens;
	public int numOfToken;

	public Message(String value) {
		this.value = value.replaceAll(" ", "");
		this.tokens = value.split(" ");
		this.numOfToken = tokens.length;
	}
	
	public String getOriginMessage() {
		String origin = "";
		for(String s : tokens) {
			origin = origin + s + " ";
		}
		return origin;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Message{");
		sb.append("value='").append(value).append('\'');
		sb.append(", tokens=").append(Arrays.toString(tokens));
		sb.append(", numOfToken=").append(numOfToken);
		sb.append('}');
		return sb.toString();
	}
}
