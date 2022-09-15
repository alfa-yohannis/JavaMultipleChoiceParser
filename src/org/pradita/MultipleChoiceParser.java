package org.pradita;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.GoogleJavaFormatToolProvider;
import com.google.googlejavaformat.java.JavaFormatterOptions;
import com.google.googlejavaformat.java.JavaFormatterOptions.Style;
import com.opencsv.CSVWriter;

public class MultipleChoiceParser {

	private static final String CHOICE_SEPARATOR = "###";

	/***
	 * 
	 * @param args
	 * @throws IOException
	 * @throws FormatterException
	 */
	public static void main(String[] args) throws IOException, FormatterException {

		List<Question> questions = new ArrayList<>();

//		String[] links = Files.readString(Path.of("." + File.separator + "literals_and_variables.txt")).split("\n");
		File linksFile = new File("access_control.txt");
//		File linksFile = new File("classes.txt");
		String[] links = Files.readString(Path.of(linksFile.getPath())).split("\n");

		for (String link : links) {
			Document doc = Jsoup.connect(link).get();
//			Document doc = Jsoup.connect("https://www.sanfoundry.com/java-mcqs-access-control/").get();
//			Document doc = Jsoup.parse(new File("file.html"));
			Elements elements = doc.getElementsByTag("p");
			String text = "";
			for (int i = 0; i < elements.size(); i++) {
				Element element = elements.get(i);
				if (element.text().contains("a) ") && element.text().contains("b) ")) {
					String code = null;
					if (element.previousElementSibling().tagName().equals("div")
							&& element.previousElementSibling().className().equals("hk1_style-wrap5")) {
						code = element.previousElementSibling().text();
					}
					text = text + element.text();

					if (!text.contains(" c) ")) {
						text = text.replace("View Answer", " c)  View Answer");
					}
					if (!text.contains(" d) ")) {
						text = text.replace("View Answer", " d)  View Answer");
					}

					String answer = "";
					if (text.contains("View Answer")) {
						answer = element.nextElementSibling().text();
					} else {
						answer = " " + element.nextElementSibling().nextElementSibling().text();
						answer = answer + " "
								+ element.nextElementSibling().nextElementSibling().nextElementSibling().text();
						System.console();
					}

					text = text + " " + answer;
					questions.add(createQuestion(text, code, link));
					text = "";
				} else {
					String x = (element.text().length() > 0) ? String.valueOf(element.text().charAt(0)) : "";
					try {
						// x is integer
						if (x.equals("6")) {
							System.console();
						}
						Integer.parseInt(x);
						if (i + 1 < elements.size() && elements.get(i + 1).text().contains("a) ")
								&& elements.get(i + 1).text().contains("b) ")) {
							text = text + element.text() + " ";
						}
					} catch (Exception e) {

					}

				}
			}
		}

//		

		writeToCsv(linksFile, questions);
		for (Question q : questions) {
			System.out.println(q);
			System.out.println();
		}
		System.out.println("Finished!");
	}

	/***
	 * 
	 * @param linksFile
	 * @param questions
	 */
	private static void writeToCsv(File linksFile, List<Question> questions) {
		File file = new File(linksFile.getAbsolutePath().replace(".txt", ".csv"));
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
//			String[] header = { "Question", "Description", "Type", "Required", "Option Start", "Option 2", "Option 3",
//					"Option End", "Correct Answer", "Point", "Correct Feedback" };			
			String[] header = { "Question Type", "Question", "Question Help Text", "Required Question? Y/N",
					"URL of Image or Youtube video", "Choices", "Other option (Y/N)", "correct option(s)",
					"Lower Bound", "Upper Bound", "Lower Bound Label", "Upper Bound Label", "Grid Rows", "Grid Columns",
					"Validation Type", "Validation Rule", "Validation Parameter", "Parameter 2",
					"Validation Error Message", "Points", "Feedback Text", "Link", "Link Text", "Feedback Text", "Link",
					"Link Text", "Feedback Text" };

			writer.writeNext(header);
			for (Question q : questions) {
				String[] data = { "Multiple Choice Question", format(
						(q.getCode() == null) ? q.getText() : q.getText() + System.lineSeparator() + q.getCode()), "",
						"Yes", "",
						format(q.getA() + CHOICE_SEPARATOR + q.getB() + CHOICE_SEPARATOR + q.getC() + CHOICE_SEPARATOR
								+ q.getD()),
						"", format(getCorrectAnswerValue(q)), "", "", "", "", "", "", "", "", "", "", "", "1",
						format(q.getExplanation()), "", "", format(q.getExplanation()), "", "",
						format(q.getExplanation()) };
				writer.writeNext(data);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String getCorrectAnswerValue(Question question) {
		String value = null;
		switch (question.getAnswer().trim().toLowerCase()) {
		case "a":
			value = question.getA();
			break;
		case "b":
			value = question.getB();
			break;
		case "c":
			value = question.getC();
			break;
		case "d":
			value = question.getD();
			break;
		default:
			break;
		}
		return value;
	}

	private static String format(String text) {
		text = text.replace("“", "\"");
		text = text.replace("”", "\"");
		text = text.replace("’", "\'");
		text = text.replace("\u8220", "\"");
		text = text.replace("\u8221", "\"");
		text = text.replace("\u8217", "\'");
		text = text.replace("\u00a0", " ");
		return text;
	}

	private static Question createQuestion(String text, String code, String link) throws FormatterException {
		Question q = new Question();
		String[] parts = text.split(" [abcd]\\) | View Answer Answer: | Explanation: |\\d\\. ");
		if (code != null) {
			JavaFormatterOptions options = JavaFormatterOptions.builder().style(Style.AOSP).build();
			Formatter formatter = new Formatter(options);
			try {
				code = formatter.formatSource(code);
			} catch (Exception e) {
			}
			System.console();
		}
		System.out.println(parts[1]);
		q.setText(parts[1]);
		q.setCode(code);
		q.setA(parts[2]);
		q.setB(parts[3]);
		q.setC(parts[4]);
		q.setD(parts[5]);
		q.setAnswer(parts[6]);
		q.setExplanation(parts[7]);
		q.setSubtopic(link);

		return q;
	}

}
