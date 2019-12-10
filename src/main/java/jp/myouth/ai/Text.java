package jp.myouth.ai;

import java.util.List;

import com.amazonaws.services.comprehend.model.DominantLanguage;

public class Text {

	public Boolean checkModeration(String text) throws Exception {

		DetectDominantLanguage lang = new DetectDominantLanguage();
		List<DominantLanguage> dominantLanguages = lang.text(text);

		String[] result = null;
		
		for(int i = 0; i < dominantLanguages.size(); i++)
		result = dominantLanguages.get(i).toString().split(",");
		String sourceLanguageCode = result[0].replace("{LanguageCode: ", "");
		System.out.println(sourceLanguageCode);
		
		String translatedText = null;
		if(sourceLanguageCode != "en") {
			Translate translate = new Translate();
			translatedText = translate.text(text, sourceLanguageCode, "en");
		} else {
			translatedText = text;
		}

		String[] words = translatedText.split(" ");
		for(String word: words)
			System.out.println(word.toLowerCase().replace(".",""));
		
		return true;
	}

	public static void main(String[] args) throws Exception {

		Text text = new Text();
		text.checkModeration("Salope");
	}
}
