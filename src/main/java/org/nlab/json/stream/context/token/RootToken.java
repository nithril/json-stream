package org.nlab.json.stream.context.token;

/**
 * Created by nlabrot on 19/04/16.
 */
public class RootToken implements Token {

	private Token value;

	@Override
	public Token getChildren() {
		return value;
	}

	@Override
	public void setChildren(Token value) {
		this.value = value;
	}

	@Override
	public boolean isLiteral() {
		return false;
	}
}
