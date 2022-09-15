package org.pradita;

public class Question {

	private String topic;
	private String subtopic;
	private String text;
	private String answer;
	private String explanation;

	private String a;
	private String b;
	private String c;
	private String d;
	private String code;

	@Override
	public String toString() {
		return text + System.lineSeparator() + ((code == null) ? "" : code + System.lineSeparator()) + "a) " + a
				+ System.lineSeparator() + "b) " + b + System.lineSeparator() + "c) " + c + System.lineSeparator()
				+ "d) " + d + System.lineSeparator() + "Anwer:" + System.lineSeparator() + answer
				+ System.lineSeparator() + "Explanation:" + System.lineSeparator() + explanation;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSubtopic() {
		return subtopic;
	}

	public void setSubtopic(String subtopic) {
		this.subtopic = subtopic;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
