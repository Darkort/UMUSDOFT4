package Classes.DataBase;

/**
 * Contains an score integer, auxiliar class of SQLite Database
 * @author AlexCantos
 * @version 1.0
 */
public class Score {
    int score;



    public Score(int score){
        this.score=score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
