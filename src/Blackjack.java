import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class Blackjack {
	Scanner input = new Scanner(System.in);
	int haveMoney, betMoney, p_ACount, c_ACount, p_CardN, c_CardN, p_Score, c_Score;
	int[] all_Score=new int[52], sf_Score;
	String[] all_Card=new String[52], p_Card, c_Card, sf_Card;
	
	public Blackjack() {
		haveMoney = 1000000;
		String[] sDHK = {"♠", "◆", "♥", "♣"};
		String[] num = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		
		int a=0, i, j;
		for (i = 0; i < sDHK.length; i++) {
			for(j=0; j<num.length; j++) {
				all_Card[a] = sDHK[i];
				all_Card[a++] += num[j];
			}
		}
		a=0;
		int[] sc={11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
		for(i=0; i<4; i++) {
			for(j=0;j<13;j++) {
				all_Score[a++]=sc[j];
			}
		}
		resetB();
	}
	
	public void resetB() {
		betMoney = betting();
		p_ACount = c_ACount = 0;
		p_CardN = c_CardN = 0;
		p_Score = c_Score = 0;
		sf_Card = new String[52];
		sf_Score = new int[52];
		cardShuffle();
		p_Card = new String[11];
		c_Card = new String[11];
		if(betMoney != 0) {
			startB();
		}
		else {
			System.out.println("소지금 부족 Game over");
		}
	}
	

	public int betting() {
		System.out.println("배팅 금액 입력 (최대 배팅금액:" + haveMoney/10 + ") ");
		//배팅값 입력
		int a = 0;
		boolean isOk=true;
		while(isOk) {
			a=input.nextInt();
			if(a<= (haveMoney/10) && a>=0 )
				isOk = false;
		}
		haveMoney -= a;
		return a;
	}
	
	private void cardShuffle() {
		int i=0,a;
		Random rd = new Random();
		while(i<all_Card.length) {
			a=rd.nextInt(52);
			//
			if(! card_Same(a)) {
				sf_Card[i]=all_Card[a];
				sf_Score[i++]=all_Score[a];
			}
		}
	}

	private boolean card_Same(int a) {
		for(int i=0; i<all_Card.length; i++)
			if(sf_Card[i]==all_Card[a])
				return true;
		return false;
	}
	
	public void card_Get(boolean a) {
		int b=p_CardN+c_CardN;
		if(a)
			p_Card[p_CardN++] = sf_Card[b];
		else
			c_Card[c_CardN++] = sf_Card[b];
		score_Set(this.sf_Score[b],a);
		show_Card();
	}
	


	public void show_Card() {
		int i;
		System.out.println("현재 카드 보유 상황 ");
		System.out.println("내 카드:");
		for(i=0; i<p_CardN; i++) {
			System.out.println(p_Card[i]+" ");
		}
		System.out.println("스코어 :"+p_Score+" 컴카드:");
		for(i=0; i<c_CardN; i++)
			System.out.println(c_Card[i]+ " ");
		if(i>2)
			System.out.println("스코어 :"+c_Score);
		else
			System.out.println(); //?
	}
	
	public void score_Set(int a, boolean b) {
		if(b)
			p_Score += a;
		else
			c_Score += a;
		if(a==11)
			if(b)
				p_ACount++;
			else
				c_ACount++;
		if(p_Score>21 && p_ACount >0) {
			p_ACount--;
			p_Score -= 10;
		}
		if(c_Score > 21 && c_ACount>0) {
			c_ACount--;
			c_Score -= 10;
		}
	}
	
	public void startB() {
		int a=0;
		card_Get(false);
		card_Get(true);
		card_Get(true);
		card_Get(false);
		
		blackTF();
		p_Mode();
		c_Mode();
		wOrL();
		
	}

	public void blackTF() {
		int a=0;
		if(p_Score ==21) {
			System.out.println("\n플레이어 블랙잭");
			a++;
		}
		if(c_Score ==21) {
			System.out.println("\n컴퓨터 블랙잭");
			a+=2;
		}
		if(a!=0)
			wL(a);
	}

	public void wL(int a) {
		if(a==1) {
			haveMoney += betMoney*1.5;
			System.out.println("승리");
		}
		
		else if(a==2)
			System.out.println("패배");
		else {
			haveMoney += betMoney;
			System.out.println("무승부");
		}
		
		resetB();
	}
	
	public void p_Mode() {
		while(true) {
			System.out.println("1.힛  2.스테이");
			int a = input.nextInt();
			if(a ==1) {
				card_Get(true);
				if(p_Score>21)
					wL(2);
			}
			else if(a==2)
				break;
			else
				System.out.println("재입력");
		}
	}
	
	public void c_Mode() {
		while(true) {
			if(c_Score <= 21 && c_Score >= 17)
				wOrL();
			else if(c_Score > 21)
				wL(1);
			else
				card_Get(false);
		}
	}
	
	public void wOrL() {
		if(p_Score == c_Score)
			wL(3);
		else if(p_Score > c_Score)
			wL(1);
		else
			wL(2);
	}
	
	

}
