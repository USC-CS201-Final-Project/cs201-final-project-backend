package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class DatabaseManager {
	
	public Connection conn = null;
	public Statement st = null;
	public ResultSet rs = null;
	
	private final String SCHEMA = "typer";
	private final String USER = "root";
	private final String PASSWORD = "mysqlr00t";
	private final int DEFAULTCOSTUME = 0;
	
	private final String[] words = {"ability", "able", "about", "above", "accept", "according", "account", "across", "act", "action", "activity", "actually", "add", "address", "administration", "admit", "adult", "affect", "after", "again", "against", "age", "agency", "agent", "ago", "agree", "agreement", "ahead", "air", "all", "allow", "almost", "alone", "along", "already", "also", "although", "always", "among", "amount", "analysis", "and", "animal", "another", "answer", "any", "anyone", "anything", "appear", "apply", "approach", "area", "argue", "arm", "around", "arrive", "art", "article", "artist", "as", "ask", "assume", "at", "attack", "attention", "attorney", "audience", "author", "authority", "available", "avoid", "away", "baby", "back", "bad", "bag", "ball", "bank", "bar", "base", "be", "beat", "beautiful", "because", "become", "bed", "before", "begin", "behavior", "behind", "believe", "benefit", "best", "better", "between", "beyond", "big", "bill", "billion", "bit", "black", "blood", "blue", "board", "body", "book", "born", "both", "box", "boy", "break", "bring", "brother", "budget", "build", "building", "business", "but", "buy", "by", "call", "camera", "campaign", "can", "cancer", "candidate", "capital", "car", "card", "care", "career", "carry", "case", "catch", "cause", "cell", "center", "central", "century", "certain", "certainly", "chair", "challenge", "chance", "change", "character", "charge", "check", "child", "choice", "choose", "church", "citizen", "city", "civil", "claim", "class", "clear", "clearly", "close", "coach", "cold", "collection", "college", "color", "come", "commercial", "common", "community", "company", "compare", "computer", "concern", "condition", "conference", "consider", "consumer", "contain", "continue", "control", "cost", "could", "country", "couple", "course", "court", "cover", "create", "crime", "cultural", "culture", "cup", "current", "customer", "cut", "dark", "data", "daughter", "day", "dead", "deal", "death", "debate", "decade", "decide", "decision", "deep", "defense", "degree", "democratic", "describe", "design", "despite", "detail", "determine", "develop", "development", "die", "difference", "different", "difficult", "dinner", "direction", "director", "discover", "discuss", "discussion", "disease", "do", "doctor", "dog", "door", "down", "draw", "dream", "drive", "drop", "drug", "during", "each", "early", "east", "easy", "eat", "economic", "economy", "edge", "education", "effect", "effort", "eight", "either", "election", "else", "employee", "end", "energy", "enjoy", "enough", "enter", "entire", "environment", "environmental", "especially", "establish", "even", "evening", "event", "ever", "every", "everybody", "everyone", "everything", "evidence", "exactly", "example", "executive", "exist", "expect", "experience", "expert", "explain", "eye", "face", "fact", "factor", "fail", "fall", "family", "far", "fast", "father", "fear", "federal", "feel", "feeling", "few", "field", "fight", "figure", "fill", "film", "final", "finally", "financial", "find", "fine", "finger", "finish", "fire", "firm", "first", "fish", "five", "floor", "fly", "focus", "follow", "food", "foot", "for", "force", "foreign", "forget", "form", "former", "forward", "four", "free", "friend", "from", "front", "full", "fund", "future", "game", "garden", "gas", "general", "generation", "get", "girl", "give", "glass", "go", "goal", "good", "government", "great", "green", "ground", "group", "grow", "growth", "guess", "gun", "guy", "hair", "half", "hand", "hang", "happen", "happy", "hard", "have", "he", "head", "health", "hear", "heart", "heat", "heavy", "help", "her", "here", "herself", "high", "him", "himself", "his", "history", "hit", "hold", "home", "hope", "hospital", "hot", "hotel", "hour", "house", "how", "however", "huge", "human", "hundred", "husband", "idea", "identify", "if", "image", "imagine", "impact", "important", "improve", "in", "include", "including", "increase", "indeed", "indicate", "individual", "industry", "information", "inside", "instead", "institution", "interest", "interesting", "international", "interview", "into", "investment", "involve", "issue", "it", "item", "its", "itself", "job", "join", "just", "keep", "key", "kid", "kill", "kind", "kitchen", "know", "knowledge", "land", "language", "large", "last", "late", "later", "laugh", "law", "lawyer", "lay", "lead", "leader", "learn", "least", "leave", "left", "leg", "legal", "less", "let", "letter", "level", "lie", "life", "light", "like", "likely", "line", "list", "listen", "little", "live", "local", "long", "look", "lose", "loss", "lot", "love", "low", "machine", "magazine", "main", "maintain", "major", "majority", "make", "man", "manage", "management", "manager", "many", "market", "marriage", "material", "matter", "may", "maybe", "me", "mean", "measure", "media", "medical", "meet", "meeting", "member", "memory", "mention", "message", "method", "middle", "might", "military", "million", "mind", "minute", "miss", "mission", "model", "modern", "moment", "money", "month", "more", "morning", "most", "mother", "mouth", "move", "movement", "movie", "much", "music", "must", "my", "myself", "name", "nation", "national", "natural", "nature", "near", "nearly", "necessary", "need", "network", "never", "new", "news", "newspaper", "next", "nice", "night", "no", "none", "nor", "north", "not", "note", "nothing", "notice", "now", "number", "occur", "of", "off", "offer", "office", "officer", "official", "often", "oil", "ok", "old", "on", "once", "one", "only", "onto", "open", "operation", "opportunity", "option", "or", "order", "organization", "other", "others", "our", "out", "outside", "over", "own", "owner", "page", "pain", "painting", "paper", "parent", "part", "participant", "particular", "particularly", "partner", "party", "pass", "past", "patient", "pattern", "pay", "peace", "people", "per", "perform", "performance", "perhaps", "period", "person", "personal", "phone", "physical", "pick", "picture", "piece", "place", "plan", "plant", "play", "player", "point", "police", "policy", "political", "politics", "poor", "popular", "population", "position", "positive", "possible", "power", "practice", "prepare", "present", "president", "pressure", "pretty", "prevent", "price", "private", "probably", "problem", "process", "produce", "product", "production", "professional", "professor", "program", "project", "property", "protect", "prove", "provide", "public", "pull", "purpose", "push", "put", "quality", "question", "quickly", "quite", "race", "radio", "raise", "range", "rate", "rather", "reach", "read", "ready", "real", "reality", "realize", "really", "reason", "receive", "recent", "recently", "recognize", "record", "red", "reduce", "reflect", "region", "relate", "relationship", "religious", "remain", "remember", "remove", "report", "represent", "require", "research", "resource", "respond", "response", "responsibility", "rest", "result", "return", "reveal", "rich", "right", "rise", "risk", "road", "rock", "role", "room", "rule", "run", "safe", "same", "save", "say", "scene", "school", "science", "scientist", "score", "sea", "season", "seat", "second", "section", "security", "see", "seek", "seem", "sell", "send", "senior", "sense", "series", "serious", "serve", "service", "set", "seven", "several", "shake", "share", "she", "shoot", "short", "shot", "should", "shoulder", "show", "side", "sign", "significant", "similar", "simple", "simply", "since", "sing", "single", "sister", "sit", "site", "situation", "six", "size", "skill", "skin", "small", "smile", "so", "social", "society", "soldier", "some", "somebody", "someone", "something", "sometimes", "son", "song", "soon", "sort", "sound", "source", "south", "southern", "space", "speak", "special", "specific", "speech", "spend", "sport", "spring", "staff", "stage", "stand", "standard", "star", "start", "state", "statement", "station", "stay", "step", "still", "stock", "stop", "store", "story", "strategy", "street", "strong", "structure", "student", "study", "stuff", "style", "subject", "success", "successful", "such", "suddenly", "suffer", "suggest", "summer", "support", "sure", "surface", "system", "table", "take", "talk", "task", "tax", "teach", "teacher", "team", "technology", "television", "tell", "ten", "tend", "term", "test", "than", "thank", "that", "the", "their", "them", "themselves", "then", "theory", "there", "these", "they", "thing", "think", "third", "this", "those", "though", "thought", "thousand", "threat", "three", "through", "throughout", "throw", "thus", "time", "to", "today", "together", "tonight", "too", "top", "total", "tough", "toward", "town", "trade", "traditional", "training", "travel", "treat", "treatment", "tree", "trial", "trip", "trouble", "true", "truth", "try", "turn", "two", "type", "under", "understand", "unit", "until", "up", "upon", "us", "use", "usually", "value", "various", "very", "victim", "view", "violence", "visit", "voice", "vote", "wait", "walk", "wall", "want", "war", "watch", "water", "way", "we", "weapon", "wear", "week", "weight", "well", "west", "western", "what", "whatever", "when", "where", "whether", "which", "while", "white", "who", "whole", "whom", "whose", "why", "wide", "wife", "will", "win", "wind", "window", "wish", "with", "within", "without", "woman", "wonder", "word", "work", "worker", "world", "worry", "would", "write", "writer", "wrong", "yard", "yeah", "year", "yes", "yet", "you", "young", "your", "yourself"};
	private Random random = null;
	
	/*
	 * Name ------------- DatabaseManager()
	 * Parameters ------- None
	 * Return Type ------ DatabaseManager
	 * Global Variables - Connection conn, Statement st, Random random
	 * Description ------ Constructs Database Manager to run all further functions.
	 */
	public DatabaseManager() {
		try {	
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + SCHEMA + "?user=" + USER + "&password=" + PASSWORD);
			st = conn.createStatement();
			
			random = new Random();

			createTable();
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
	}
	
	/*
	 * Name ------------- createTable()
	 * Parameters ------- None
	 * Return Type ------ Void
	 * Global Variables - Statement st
	 * Description ------ Creates empty table called Account in SQL Database
	 */
	public void createTable() {
		try {	
			st.execute("CREATE TABLE Accounts (\n"
						+ "UserName varchar(255) NOT NULL,\n"
						+ "Password varchar(255) NOT NULL,\n"
						+ "CostumeID int NOT NULL,\n"
						+ "PRIMARY Key (UserName)\n"
						+ ");");
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
	}
	
	/*
	 * Name ------------- dropTable()
	 * Parameters ------- None
	 * Return Type ------ Void
	 * Global Variables - Statement st
	 * Description ------ Deletes Account table in SQL Database if it exists,
	 *						else does nothing.
	 */
	public void dropTable() {
		try {	
			st.execute("DROP TABLE Accounts;");
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
	}
	
	/*
	 * Name ------------- getWord()
	 * Parameters ------- None
	 * Return Type ------ String
	 * Global Variables - String[] words
	 * Description ------ Returns random word between 2 and 14 characters 
	 *						(Avg length is ~5.5 characters)
	 */
	public String getWord() {
		return words[random.nextInt(986)];
	}
	
	/*
	 * Name ------------- getCostumeID()
	 * Parameters ------- String username
	 * Return Type ------ Integer
	 * Global Variables - Statement st, ResultSet rs
	 * Description ------ Finds CostumeID for given username in SQL Database
	 *						and returns null if username is not found.
	 */
	public Integer getCostumeID(String username) {
		try {
			rs = st.executeQuery("SELECT UserName, CostumeID\n"
								+ "FROM Accounts\n"
								+ "WHERE UserName='" + username + "';");
			
			String name = null;
			Integer id = null;
			while (rs.next()) {
				name = rs.getString("UserName");
				id = rs.getInt("CostumeID");
			}

			if(username.equals(name)) {
				return id;
			}
			
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage() + "\n" +
						"Recheck for Existsing Username.");
		}
		
		return 0;
	}
	
	/*
	 * Name ------------- changeCostumeID()
	 * Parameters ------- String username, int costumeID
	 * Return Type ------ Void
	 * Global Variables - Statement st
	 * Description ------ Changes CostumeID for given username in SQL Database
	 *						and changes nothing if username is not found.
	 */
	public void changeCostumeID(String username, int costumeID) {
		try {
			st.executeUpdate("UPDATE Accounts\n"
								+ "SET CostumeID=" + costumeID + "\n"
								+ "WHERE UserName='" + username + "';");
			
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage() + "\n" +
						"Recheck for Existsing Username.");
		}
	}
	
	/*
	 * Name ------------- userExists()
	 * Parameters ------- String username
	 * Return Type ------ boolean
	 * Global Variables - Statement st, ResultSet rs
	 * Description ------ Searches for username within SQL Database,
	 *						returns false if not found,
	 *						returns true if found.
	 */
	public boolean userExists(String username) {
		try {
			rs = st.executeQuery("SELECT UserName\n"
								+ "FROM Accounts\n"
								+ "WHERE UserName='" + username + "';");
			
			String name = null;
			while (rs.next()) {
				name = rs.getString("UserName");
			}

			if(username.equals(name)) {
				return true;
			}
			
			return false;
			
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage() + "\n" +
						"Recheck for Existsing Username.");
		}
		
		return true;
	}
	
	/*
	 * Name ------------- authenticateUser()
	 * Parameters ------- String username, String password
	 * Return Type ------ boolean
	 * Global Variables - Statement st, ResultSet rs
	 * Description ------ Searches for username within SQL Database and compares stored Password,
	 *						returns true if found and password matches,
	 *						returns false if username not found or password does not match.
	 */
	public boolean authenticateUser(String username, String password) {
		try {
			rs = st.executeQuery("SELECT UserName, Password\n"
								+ "FROM Accounts\n"
								+ "WHERE UserName='" + username + "';");
			
			String expName = null;
			String expPassword = null;
			while (rs.next()) {
				expName = rs.getString("UserName");
				expPassword = rs.getString("Password");
			}
			System.out.println(expName);
			System.out.println(expPassword);

			if(username.equals(expName)) {
				if(password.equals(expPassword)) {
					System.out.println("Authenticated");
					return true;
				}
			}
			System.out.println("Not Authenticated");
			return false;
			
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		//
		return false;
	}
	
	/*
	 * Name ------------- createUser()
	 * Parameters ------- String username, String password
	 * Return Type ------ boolean
	 * Global Variables - Statement st
	 * Description ------ Inserts new username and password into SQL Database if username does not already exists,
	 *						returns true if information is added,
	 *						returns false if username already exist.
	 */
	public boolean createUser(String username, String password) {
		try {
			if(!userExists(username)) {
				st.executeUpdate("INSERT INTO Accounts (UserName, Password, CostumeID)\n"
								+ "VALUES ('" + username + "', '" + password + "', " + DEFAULTCOSTUME + ");");
				return true;
			}
			
			return false;

		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		}
	}
}
