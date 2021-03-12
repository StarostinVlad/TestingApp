package com.starostinvlad.teamof8testingapp;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.starostinvlad.teamof8testingapp.Models.Answer;
import com.starostinvlad.teamof8testingapp.Models.Question;
import com.starostinvlad.teamof8testingapp.Models.Section;
import com.starostinvlad.teamof8testingapp.Models.Theme;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class AllDataDeserializer implements JsonDeserializer<List<Section>> {
    private String TAG = getClass().getSimpleName();
    private List<Question> questions;
    private List<Answer> answers;
    private List<Theme> themes;
    private List<Section> sections;

    @Override
    public List<Section> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.d(TAG, "deserialize: json:" + json.toString());

        SectionDao sectionDao = App.getDatabase().sectionDao();
        ThemeDao themeDao = App.getDatabase().themeDao();
        QuestionDao questionDao = App.getDatabase().questionDao();
        AnswerDao answerDao = App.getDatabase().answerDao();

        sections = new ArrayList<>();
        themes = new ArrayList<>();
        questions = new ArrayList<>();
        answers = new ArrayList<>();

        if (json.isJsonArray()) {
            JsonArray sectionsJsonArray = json.getAsJsonArray();
            for (JsonElement sectionJsonElement : sectionsJsonArray) {
                sections.add(parseSection(sectionJsonElement));
            }
        } else if (json.isJsonObject()) {
            sections.add(parseSection(json));
        }

        sectionDao.insert(sections);
        themeDao.insert(themes);
        questionDao.insert(questions);
        answerDao.insert(answers);

        return sections;
    }

    private Section parseSection(JsonElement sectionJsonElement) {
        JsonObject sectionJsonObject = sectionJsonElement.getAsJsonObject();
        int sectionId = sectionJsonObject.get("id").getAsInt();
        String sectionTitle = sectionJsonObject.get("title").getAsString();
        Section section = new Section();
        section.setId(sectionId);
        section.setTitle(sectionTitle);
        Log.d(TAG, "parseSection: section inserted");
        JsonArray themesJsonArray = sectionJsonObject.get("themes").getAsJsonArray();
        for (JsonElement themeJsonElement : themesJsonArray) {
            themes.add(parseTheme(themeJsonElement, sectionId));
        }
        return section;
    }

    private Theme parseTheme(JsonElement themeJsonElement, int sectionId) {
        JsonObject themeJsonObject = themeJsonElement.getAsJsonObject();
        int themeId = themeJsonObject.get("id").getAsInt();
        String themeTitle = themeJsonObject.get("title").getAsString();
        Theme theme = new Theme();
        theme.setSectionId(sectionId);
        theme.setId(themeId);
        theme.setTitle(themeTitle);
        Log.d(TAG, "parseSection: тема: " + themeTitle + " inserted");
        JsonArray questionsJsonArray = themeJsonObject.get("questions").getAsJsonArray();
        for (JsonElement questionJsonElement : questionsJsonArray) {
            questions.add(parseQuestion(questionJsonElement, themeId));
        }
        return theme;
    }

    private Question parseQuestion(JsonElement questionJsonElement, int themeId) {
        JsonObject questionJsonObject = questionJsonElement.getAsJsonObject();
        int questionId = questionJsonObject.get("id").getAsInt();
        String questionTitle = questionJsonObject.get("title").getAsString();
        String questionImage = questionJsonObject.get("image").getAsString();

        Question question = new Question();
        question.setId(questionId);
        question.setTitle(questionTitle);
        question.setImage(questionImage);
        question.setThemeId(themeId);

        JsonArray answersJsonArray = questionJsonObject.get("answers").getAsJsonArray();
//        if (isCorrect)
//            question.setCorrectAnswer(sortPosition);
        for (JsonElement answerJsonElement : answersJsonArray) {
            Answer answer = parseAnswer(answerJsonElement, questionId);
            if (answer.isCorrect())
                question.setCorrectAnswer(answer.getSortPosition());
            answers.add(answer);
        }
        Log.d(TAG, "parseSection: вопрос: " + questionTitle + " inserted");
        return question;
    }

    private Answer parseAnswer(JsonElement answerJsonElement, int questionId) {
        JsonObject answerJsonObject = answerJsonElement.getAsJsonObject();
        int answerId = answerJsonObject.get("id").getAsInt();
        String answerTitle = answerJsonObject.get("title").getAsString();
        boolean isCorrect = answerJsonObject.get("is_correct").getAsBoolean();
        int sortPosition = answerJsonObject.get("sort_position").getAsInt();
        Answer answer = new Answer();
        answer.setId(answerId);
        answer.setTitle(answerTitle);
        answer.setIsCorrect(isCorrect);
        answer.setSortPosition(sortPosition);
        answer.setQuestionId(questionId);
        Log.d(TAG, "parseSection: ответ: " + answerTitle + " inserted");
        return answer;
    }
}
