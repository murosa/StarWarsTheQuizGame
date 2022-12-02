package com.example.starwarsquizgame;

// this class is used to store everything pertaining to the questions and containing functions that allow
// other classes to retrieve the questions contained in this class
public class Questions {
    String[] question = {"What is Baby Yoda's real name?", "Where did Obi-Wan take Luke after his birth?",
            "What is Mando''s real name from The Mandalorian?", "Who had the highest midi-chlorian count in Star Wars?",
            "What year did the first Star Wars movie come out?", "What is the name of Boba Fett's ship?",
            "Who are Kylo Ren's parents?", "Who killed Qui-Gon Jinn?",
            "According to Yoda, there are always how many Sith Lords at a given time?", "What was Finn's stormtrooper number?"
            , "C-3P0 is fluent in how many languages?", "Who killed the four Jedi Masters: Saesee Tinn, Mace Windu, Kit Fisto, and Agen Kolar?"
            , "What is the name of Yoda’s home?", "What is the name of General Grievous’ Flagship, which was not mentioned in the movie?"
            , "What is the Toydarian’s name who owned Anakin Skywalker?", "What species stole the plans to the Death Star?"
            , "Who built C-3P0?", "Who is the bounty hunter who catches Han Solo?"
            , "Who was the Jedi master that ordered to create clones?", "Where can you find the hidden rebel base?"
    };
    String[] answer1 = {
            "Eeth Koth", "Dathomir", "Jango Fett",
            "Anakin Skywalker", "1977", "Millennium Falcon",
            "Han Solo and Princess Leia", "Count Dooku", "1",
            "GZ-4619", "Over 60 million languages", "Anakin Skywalker",
            "Dathomir", "The Invisible Hand", "Watto",
            "Bothans", "Obi-wan Kenobi", "Boba Fett",
            "Mace Windu", "Hoth"
    };
    String[] answer2 = {
            "Yoda", "Tatooine", "Cad Bane",
            "Palpatine", "1985", "Ebon Hawk",
            "Luke Skywaler and Mara Jade", "Darth Maul", "2",
            "DA-4239", "Over 10 million languages", "Revan",
            "Tatooine", "Star Dreadnought Executor", "Sebulba",
            "Twi'lek", "Qui-Gon Jinn", "Aurra Sing",
            "Syfo Dyas", "Yavin IV"
    };
    String[] answer3 = {
            "Grogu", "Mustafar", "Embo",
            "Obi-Wan", "1966", "Slave 1",
            "Anakin Skywalker and Padme Amidala", "Palpatine", "3",
            "BH-1624", "Over 100 million languages", "Darth Maul",
            "Hoth", "Ravager", "Poggle",
            "Zabrak", "Anakin Skywalker", "Bossk",
            "Yarael Poof", "Endor"
    };
    String[] answer4 = {
            "Rex", "Naboo", "Din Djarin",
            "Luke Skywalker", "1980", "Razor Crest",
            "Boba Fett and Sintas Vel", "Kylo Ren", "4",
            "FN-2187", "Over 50  million languages", "Palpatine",
            "Dagobah", "Tantive IV", "Jabba",
            "Togruta", "Padme Amidala", "Cad Bane",
            "Yaddle", "Kashyyyk"
    };
    int[] correctAnswer = {3, 2, 4, 1, 1, 3, 1, 2, 2, 4, 1, 4, 4, 1, 1, 1, 3, 1, 2, 2};

    public String getQuestion(int index) {
        return question[index];
    }

    public String getAnswer1(int index) {
        return answer1[index];
    }

    public String getAnswer2(int index) {
        return answer2[index];
    }

    public String getAnswer3(int index) {
        return answer3[index];
    }

    public String getAnswer4(int index) {
        return answer4[index];
    }

    public int getCorrectAnswer(int index) {
        return correctAnswer[index];
    }

    public int getQuestionListSize()
    {
        return question.length;
    }
}
