package QuestionTests;

import Questions_DAO.*;
import junit.framework.TestCase;

import java.util.ArrayList;

public class TestQuiz extends TestCase {
    private Questions_DAO.Quiz quiz;
    private ArrayList<String> tags;
    private ArrayList<Question> questions;
    private ArrayList<ArrayList<String>> answers;
    private ArrayList<Integer> scores;
    private ArrayList<Integer> questionScores;
    private ArrayList<ArrayList<String>> correctAnswers;

    public void setUp() throws Exception {
        super.setUp();
        declareVariables();
        quiz = new Quiz("TestQuiz", "D.Gelashvili", "All","this is test quiz, feel free to take it",
                tags, questions, false, false, true, false);
    }

    private void declareVariables() {
        tags = new ArrayList<>();
        tags.add("Math");
        tags.add("Geography");
        tags.add("Biology");

        questions = new ArrayList<>();
        questions.add(new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                                                                                                    true, false));
        questions.add(new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                                                                    "3x4//12//7-2//5//36/6//6", false, false));
        questions.add(new QuestionMatching("What eats what?", "Rabbit//Chicken//Wolf//Corn//Meat//Carrot",
                                                    "Rabbit//Carrot//Chicken//Corn//Wolf//Meat", true, false));
        questions.add(new QuestionMultiAnswer("Name 5 Europian City", "London//Paris//Tbilisi//Kiev//Madrid",
                                                                                                false, false));
        questions.add(new QuestionMultiChoice("Capital city of Georgia:", "Qutaisi//Tbilisi//Batumi",
                                                                                "Tbilisi", true, true));
        questions.add(new QuestionMultiChoiceMultiAnswer("x^2 - 5x + 6 = 0. x = ?",
                                                            "2//3//1//6", "3//2", false, true));
        questions.add(new QuestionResponse("What is 3 x 4?", "12", false, true));

        answers = new ArrayList<>();
        scores = new ArrayList<>();
        questionScores = new ArrayList<>();
        questionScores.add(2);
        questionScores.add(3);
        questionScores.add(3);
        questionScores.add(5);
        questionScores.add(1);
        questionScores.add(2);
        questionScores.add(1);

        correctAnswers = new ArrayList<>();
        correctAnswers.add(questions.get(0).getAnswers());
        correctAnswers.add(questions.get(1).getAnswers());
        correctAnswers.add(questions.get(2).getAnswers());
        correctAnswers.add(questions.get(3).getAnswers());
        correctAnswers.add(questions.get(4).getAnswers());
        correctAnswers.add(questions.get(5).getAnswers());
        correctAnswers.add(questions.get(6).getAnswers());

    }

    public void testVariables() {
        assertEquals("TestQuiz", quiz.getQuizName());
        assertEquals("D.Gelashvili", quiz.getCreatorName());
        assertEquals("All", quiz.getCategory());
        assertEquals("this is test quiz, feel free to take it", quiz.getDescription());
        assertEquals(tags, quiz.getTags());
        assertFalse(quiz.isRandom());
        assertFalse(quiz.isOnePage());
        assertTrue(quiz.hasImmediateCorrection());
        assertFalse(quiz.isPracticeMode());
        assertEquals(questions, quiz.getQuestionList());
        assertEquals(17, quiz.getMaxScore());
        assertEquals(7, quiz.getTotalNumberOfQuestions());
    }

    public void testSimulateNormalQuiz() {
        assertEquals(1, quiz.getCurrentQuestionNumber());
        assertEquals(questions.get(0), quiz.getCurrentQuestion());
        assertTrue(quiz.hasNextQuestion());
        ArrayList<String> ls = new ArrayList<>();
        ls.add("5");
        ls.add("tbilisi");
        quiz.processAnswer(ls);
        assertEquals(1, quiz.getUserScore());
        answers.add(ls);
        scores.add(1);

        if (quiz.hasNextQuestion()){
            quiz.goToNextQuestion();
        }
        assertEquals(2, quiz.getCurrentQuestionNumber());
        assertEquals(questions.get(1), quiz.getCurrentQuestion());
        assertTrue(quiz.hasNextQuestion());
        ls = new ArrayList<>();
        ls.add("36/6//5");
        ls.add("3x4//6");
        ls.add("7-2//12");
        quiz.processAnswer(ls);
        assertEquals(1, quiz.getUserScore());
        answers.add(ls);
        scores.add(0);

        if (quiz.hasNextQuestion()){
            quiz.goToNextQuestion();
        }
        assertEquals(3, quiz.getCurrentQuestionNumber());
        assertEquals(questions.get(2), quiz.getCurrentQuestion());
        assertTrue(quiz.hasNextQuestion());
        ls = new ArrayList<>();
        ls.add("Rabbit//Carrot");
        ls.add("Wolf//Meat");
        ls.add("Chicken//Corn");
        quiz.processAnswer(ls);
        assertEquals(4, quiz.getUserScore());
        answers.add(ls);
        scores.add(3);

        if (quiz.hasNextQuestion()){
            quiz.goToNextQuestion();
        }
        assertEquals(4, quiz.getCurrentQuestionNumber());
        assertEquals(questions.get(3), quiz.getCurrentQuestion());
        assertTrue(quiz.hasNextQuestion());
        ls = new ArrayList<>();
        ls.add("TBILISI");
        ls.add("Berlin");
        ls.add("moscow");
        ls.add("london");
        quiz.processAnswer(ls);
        assertEquals(6, quiz.getUserScore());
        answers.add(ls);
        scores.add(2);

        if (quiz.hasNextQuestion()){
            quiz.goToNextQuestion();
        }
        assertEquals(5, quiz.getCurrentQuestionNumber());
        assertEquals(questions.get(4), quiz.getCurrentQuestion());
        assertTrue(quiz.hasNextQuestion());
        ls = new ArrayList<>();
        ls.add("Kutaisi");
        quiz.processAnswer(ls);
        assertEquals(6, quiz.getUserScore());
        answers.add(ls);
        scores.add(0);

        if (quiz.hasNextQuestion()){
            quiz.goToNextQuestion();
        }
        assertEquals(6, quiz.getCurrentQuestionNumber());
        assertEquals(questions.get(5), quiz.getCurrentQuestion());
        assertTrue(quiz.hasNextQuestion());
        ls = new ArrayList<>();
        ls.add("6");
        ls.add("3");
        ls.add("1");
        quiz.processAnswer(ls);
        assertEquals(6, quiz.getUserScore());
        answers.add(ls);
        scores.add(0);

        if (quiz.hasNextQuestion()){
            quiz.goToNextQuestion();
        }
        assertEquals(7, quiz.getCurrentQuestionNumber());
        assertEquals(questions.get(6), quiz.getCurrentQuestion());
        ls = new ArrayList<>();
        ls.add("12");
        quiz.processAnswer(ls);
        assertEquals(7, quiz.getUserScore());
        answers.add(ls);
        scores.add(1);

        assertFalse(quiz.hasNextQuestion());
        assertEquals(scores, quiz.getUserScores());
        assertEquals(answers, quiz.getUserAnswers());
        assertEquals(questionScores, quiz.getQuestionScores());
        assertEquals(correctAnswers, quiz.getCorrectAnswers());
    }

    public void testSimulatePracticeQuiz() {
        ArrayList<Question> questions1 = new ArrayList<>();
        questions1.add(new QuestionFillBlank("3 x ___ = 12 and ___ is a capital city of Georgia", "4//Tbilisi",
                true, false));
        questions1.add(new QuestionMatching("Match these two columns:", "3x4//7-2//36/6//6//5//12",
                "3x4//12//7-2//5//36/6//6", false, false));
        Quiz quiz1 = new Quiz("PracticeQuiz", "D.Gelashvili", "All","this is practice quiz, feel free to take it",
                tags, questions1, false, false, true, true);

        assertEquals(1, quiz1.getCurrentQuestionNumber());
        assertEquals(questions1.get(0), quiz1.getCurrentQuestion());
        assertTrue(quiz1.hasNextQuestion());
        ArrayList<String> ls = new ArrayList<>();
        ls.add("4");
        ls.add("tbilisi");
        quiz1.processAnswer(ls);
        if (quiz1.hasNextQuestion()){
            quiz1.goToNextQuestion();
        }

        assertEquals(2, quiz1.getCurrentQuestionNumber());
        assertEquals(questions1.get(1), quiz1.getCurrentQuestion());
        assertTrue(quiz1.hasNextQuestion());
        ls = new ArrayList<>();
        ls.add("36/6//5");
        ls.add("3x4//6");
        ls.add("7-2//12");
        quiz1.processAnswer(ls);
        if (quiz1.hasNextQuestion()){
            quiz1.goToNextQuestion();
        }

        assertEquals(1, quiz1.getCurrentQuestionNumber());
        assertEquals(questions1.get(0), quiz1.getCurrentQuestion());
        assertTrue(quiz1.hasNextQuestion());
        ls = new ArrayList<>();
        ls.add("5");
        ls.add("tbilisi");
        quiz1.processAnswer(ls);
        if (quiz1.hasNextQuestion()){
            quiz1.goToNextQuestion();
        }

        assertEquals(2, quiz1.getCurrentQuestionNumber());
        assertEquals(questions1.get(1), quiz1.getCurrentQuestion());
        assertTrue(quiz1.hasNextQuestion());
        ls = new ArrayList<>();
        ls.add("36/6//6");
        ls.add("3x4//12");
        ls.add("7-2//5");
        quiz1.processAnswer(ls);
        if (quiz1.hasNextQuestion()){
            quiz1.goToNextQuestion();
        }

        AnswerCorrectly(questions1, quiz1);

        AnswerCorrectly(questions1, quiz1);

        assertFalse(quiz1.hasNextQuestion());
    }

    private void AnswerCorrectly(ArrayList<Question> questions1, Quiz quiz1) {
        ArrayList<String> ls;
        assertEquals(1, quiz1.getCurrentQuestionNumber());
        assertEquals(questions1.get(0), quiz1.getCurrentQuestion());
        assertTrue(quiz1.hasNextQuestion());
        ls = new ArrayList<>();
        ls.add("4");
        ls.add("tbilisi");
        quiz1.processAnswer(ls);
        if (quiz1.hasNextQuestion()){
            quiz1.goToNextQuestion();
        }

        assertEquals(2, quiz1.getCurrentQuestionNumber());
        assertEquals(questions1.get(1), quiz1.getCurrentQuestion());
        ls = new ArrayList<>();
        ls.add("36/6//6");
        ls.add("3x4//12");
        ls.add("7-2//5");
        quiz1.processAnswer(ls);
        if (quiz1.hasNextQuestion()){
            quiz1.goToNextQuestion();
        }
    }
}
