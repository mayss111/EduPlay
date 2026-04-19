export interface Question {
  id?: number;
  questionText: string;
  choiceA: string;
  choiceB: string;
  choiceC: string;
  choiceD: string;
  correctChoice: string;
  explanation: string;
  subject: string;
  classLevel: number;
  difficulty: string;
}

export interface GameSubmit {
  correctAnswers: number;
  totalQuestions: number;
  subject: string;
  difficulty: string;
}

export interface GameResult {
  xpEarned: number;
  totalXp: number;
  streak: number;
  correctAnswers: number;
  totalQuestions: number;
}