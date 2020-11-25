import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ColumnPosition {

	void highColumnIDLoad(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new HighPosition());
			i++;
		}
	}
	
	void lowColumnIDLoad(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new LowPosition());
			i++;
		}
	}
	
	void Start(int NO_OF_THREADS) {
		createDDL();
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new LoadData());
			i++;
		}
	}
	
	class LowPosition implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select avg(t2) from Column_position where t0 < 512";
				  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
				int i = 0 ;
				while (i < 2000) {
					ResultSet rs = stmt.executeQuery(SQL);

					while (rs.next()) {
						
					}
					
					i++;
				}
				   dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				    now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	class HighPosition implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select avg(t234) from Column_position where t0 < 512";
				int i = 0 ;
				  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
				while (i < 2000) {
					ResultSet rs = stmt.executeQuery(SQL);
					while (rs.next()) {
						
					}
					
					i++;
				}
				   dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				    now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	void createDDL(){
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "drop table Column_position";
			try {
				stmt.execute(SQL);
			}
			catch(Exception E) {
			
			}
			SQL = "create table Column_position (T0 number primary key," + 
					"T1 number ," + 
					"T2 number," + 
					"T3 number," + 
					"T4 number," + 
					"T5 number," + 
					"T6 number," + 
					"T7 number," + 
					"T8 number," + 
					"T9 number," + 
					"T10 number," + 
					"T11 number," + 
					"T12 number," + 
					"T13 number," + 
					"T14 number," + 
					"T15 number," + 
					"T16 number," + 
					"T17 number," + 
					"T18 number," + 
					"T19 number," + 
					"T20 number," + 
					"T21 number," + 
					"T22 number," + 
					"T23 number," + 
					"T24 number," + 
					"T25 number," + 
					"T26 number," + 
					"T27 number," + 
					"T28 number," + 
					"T29 number," + 
					"T30 number," + 
					"T31 number," + 
					"T32 number," + 
					"T33 number," + 
					"T34 number," + 
					"T35 number," + 
					"T36 number," + 
					"T37 number," + 
					"T38 number," + 
					"T39 number," + 
					"T40 number," + 
					"T41 number," + 
					"T42 number," + 
					"T43 number," + 
					"T44 number," + 
					"T45 number," + 
					"T46 number," + 
					"T47 number," + 
					"T48 number," + 
					"T49 number," + 
					"T50 number," + 
					"T51 number," + 
					"T52 number," + 
					"T53 number," + 
					"T54 number," + 
					"T55 number," + 
					"T56 number," + 
					"T57 number," + 
					"T58 number," + 
					"T59 number," + 
					"T60 number," + 
					"T61 number," + 
					"T62 number," + 
					"T63 number," + 
					"T64 number," + 
					"T65 number," + 
					"T66 number," + 
					"T67 number," + 
					"T68 number," + 
					"T69 number," + 
					"T70 number," + 
					"T71 number," + 
					"T72 number," + 
					"T73 number," + 
					"T74 number," + 
					"T75 number," + 
					"T76 number," + 
					"T77 number," + 
					"T78 number," + 
					"T79 number," + 
					"T80 number," + 
					"T81 number," + 
					"T82 number," + 
					"T83 number," + 
					"T84 number," + 
					"T85 number," + 
					"T86 number," + 
					"T87 number," + 
					"T88 number," + 
					"T89 number," + 
					"T90 number," + 
					"T91 number," + 
					"T92 number," + 
					"T93 number," + 
					"T94 number," + 
					"T95 number," + 
					"T96 number," + 
					"T97 number," + 
					"T98 number," + 
					"T99 number," + 
					"T100 number," + 
					"T101 number," + 
					"T102 number," + 
					"T103 number," + 
					"T104 number," + 
					"T105 number," + 
					"T106 number," + 
					"T107 number," + 
					"T108 number," + 
					"T109 number," + 
					"T110 number," + 
					"T111 number," + 
					"T112 number," + 
					"T113 number," + 
					"T114 number," + 
					"T115 number," + 
					"T116 number," + 
					"T117 number," + 
					"T118 number," + 
					"T119 number," + 
					"T120 number," + 
					"T121 number," + 
					"T122 number," + 
					"T123 number," + 
					"T124 number," + 
					"T125 number," + 
					"T126 number," + 
					"T127 number," + 
					"T128 number," + 
					"T129 number," + 
					"T130 number," + 
					"T131 number," + 
					"T132 number," + 
					"T133 number," + 
					"T134 number," + 
					"T135 number," + 
					"T136 number," + 
					"T137 number," + 
					"T138 number," + 
					"T139 number," + 
					"T140 number," + 
					"T141 number," + 
					"T142 number," + 
					"T143 number," + 
					"T144 number," + 
					"T145 number," + 
					"T146 number," + 
					"T147 number," + 
					"T148 number," + 
					"T149 number," + 
					"T150 number," + 
					"T151 number," + 
					"T152 number," + 
					"T153 number," + 
					"T154 number," + 
					"T155 number," + 
					"T156 number," + 
					"T157 number," + 
					"T158 number," + 
					"T159 number," + 
					"T160 number," + 
					"T161 number," + 
					"T162 number," + 
					"T163 number," + 
					"T164 number," + 
					"T165 number," + 
					"T166 number," + 
					"T167 number," + 
					"T168 number," + 
					"T169 number," + 
					"T170 number," + 
					"T171 number," + 
					"T172 number," + 
					"T173 number," + 
					"T174 number," + 
					"T175 number," + 
					"T176 number," + 
					"T177 number," + 
					"T178 number," + 
					"T179 number," + 
					"T180 number," + 
					"T181 number," + 
					"T182 number," + 
					"T183 number," + 
					"T184 number," + 
					"T185 number," + 
					"T186 number," + 
					"T187 number," + 
					"T188 number," + 
					"T189 number," + 
					"T190 number," + 
					"T191 number," + 
					"T192 number," + 
					"T193 number," + 
					"T194 number," + 
					"T195 number," + 
					"T196 number," + 
					"T197 number," + 
					"T198 number," + 
					"T199 number," + 
					"T200 number," + 
					"T201 number," + 
					"T202 number," + 
					"T203 number," + 
					"T204 number," + 
					"T205 number," + 
					"T206 number," + 
					"T207 number," + 
					"T208 number," + 
					"T209 number," + 
					"T210 number," + 
					"T211 number," + 
					"T212 number," + 
					"T213 number," + 
					"T214 number," + 
					"T215 number," + 
					"T216 number," + 
					"T217 number," + 
					"T218 number," + 
					"T219 number," + 
					"T220 number," + 
					"T221 number," + 
					"T222 number," + 
					"T223 number," + 
					"T224 number," + 
					"T225 number," + 
					"T226 number," + 
					"T227 number," + 
					"T228 number," + 
					"T229 number," + 
					"T230 number," + 
					"T231 number," + 
					"T232 number," + 
					"T233 number," + 
					"T234 number," + 
					"T235 number)";
			stmt.execute(SQL);
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	class LoadData implements Runnable{

		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "insert into Column_position(T0,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26,T27,T28,T29,T30,T31,T32,T33,T34,T35,T36,T37,T38,T39,T40,T41,T42,T43,T44,T45,T46,T47,T48,T49,T50,T51,T52,T53,T54,T55,T56,T57,T58,T59,T60,T61,T62,T63,T64,T65,T66,T67,T68,T69,T70,T71,T72,T73,T74,T75,T76,T77,T78,T79,T80,T81,T82,T83,T84,T85,T86,T87,T88,T89,T90,T91,T92,T93,T94,T95,T96,T97,T98,T99,T100,T101,T102,T103,T104,T105,T106,T107,T108,T109,T110,T111,T112,T113,T114,T115,T116,T117,T118,T119,T120,T121,T122,T123,T124,T125,T126,T127,T128,T129,T130,T131,T132,T133,T134,T135,T136,T137,T138,T139,T140,T141,T142,T143,T144,T145,T146,T147,T148,T149,T150,T151,T152,T153,T154,T155,T156,T157,T158,T159,T160,T161,T162,T163,T164,T165,T166,T167,T168,T169,T170,T171,T172,T173,T174,T175,T176,T177,T178,T179,T180,T181,T182,T183,T184,T185,T186,T187,T188,T189,T190,T191,T192,T193,T194,T195,T196,T197,T198,T199,T200,T201,T202,T203,T204,T205,T206,T207,T208,T209,T210,T211,T212,T213,T214,T215,T216,T217,T218,T219,T220,T221,T222,T223,T224,T225,T226,T227,T228,T229,T230,T231,T232,T233,T234,T235) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 100000) {
					pstmt.setInt(1,oraSequence.nextVal());
					pstmt.setInt(2,OraRandom.randomUniformInt(100));
					pstmt.setInt(3,OraRandom.randomUniformInt(100));
					pstmt.setInt(4,OraRandom.randomUniformInt(100));
					pstmt.setInt(5,OraRandom.randomUniformInt(100));
					pstmt.setInt(6,OraRandom.randomUniformInt(100));
					pstmt.setInt(7,OraRandom.randomUniformInt(100));
					pstmt.setInt(8,OraRandom.randomUniformInt(100));
					pstmt.setInt(9,OraRandom.randomUniformInt(100));
					pstmt.setInt(10,OraRandom.randomUniformInt(100));
					pstmt.setInt(11,OraRandom.randomUniformInt(100));
					pstmt.setInt(12,OraRandom.randomUniformInt(100));
					pstmt.setInt(13,OraRandom.randomUniformInt(100));
					pstmt.setInt(14,OraRandom.randomUniformInt(100));
					pstmt.setInt(15,OraRandom.randomUniformInt(100));
					pstmt.setInt(16,OraRandom.randomUniformInt(100));
					pstmt.setInt(17,OraRandom.randomUniformInt(100));
					pstmt.setInt(18,OraRandom.randomUniformInt(100));
					pstmt.setInt(19,OraRandom.randomUniformInt(100));
					pstmt.setInt(20,OraRandom.randomUniformInt(100));
					pstmt.setInt(21,OraRandom.randomUniformInt(100));
					pstmt.setInt(22,OraRandom.randomUniformInt(100));
					pstmt.setInt(23,OraRandom.randomUniformInt(100));
					pstmt.setInt(24,OraRandom.randomUniformInt(100));
					pstmt.setInt(25,OraRandom.randomUniformInt(100));
					pstmt.setInt(26,OraRandom.randomUniformInt(100));
					pstmt.setInt(27,OraRandom.randomUniformInt(100));
					pstmt.setInt(28,OraRandom.randomUniformInt(100));
					pstmt.setInt(29,OraRandom.randomUniformInt(100));
					pstmt.setInt(30,OraRandom.randomUniformInt(100));
					pstmt.setInt(31,OraRandom.randomUniformInt(100));
					pstmt.setInt(32,OraRandom.randomUniformInt(100));
					pstmt.setInt(33,OraRandom.randomUniformInt(100));
					pstmt.setInt(34,OraRandom.randomUniformInt(100));
					pstmt.setInt(35,OraRandom.randomUniformInt(100));
					pstmt.setInt(36,OraRandom.randomUniformInt(100));
					pstmt.setInt(37,OraRandom.randomUniformInt(100));
					pstmt.setInt(38,OraRandom.randomUniformInt(100));
					pstmt.setInt(39,OraRandom.randomUniformInt(100));
					pstmt.setInt(40,OraRandom.randomUniformInt(100));
					pstmt.setInt(41,OraRandom.randomUniformInt(100));
					pstmt.setInt(42,OraRandom.randomUniformInt(100));
					pstmt.setInt(43,OraRandom.randomUniformInt(100));
					pstmt.setInt(44,OraRandom.randomUniformInt(100));
					pstmt.setInt(45,OraRandom.randomUniformInt(100));
					pstmt.setInt(46,OraRandom.randomUniformInt(100));
					pstmt.setInt(47,OraRandom.randomUniformInt(100));
					pstmt.setInt(48,OraRandom.randomUniformInt(100));
					pstmt.setInt(49,OraRandom.randomUniformInt(100));
					pstmt.setInt(50,OraRandom.randomUniformInt(100));
					pstmt.setInt(51,OraRandom.randomUniformInt(100));
					pstmt.setInt(52,OraRandom.randomUniformInt(100));
					pstmt.setInt(53,OraRandom.randomUniformInt(100));
					pstmt.setInt(54,OraRandom.randomUniformInt(100));
					pstmt.setInt(55,OraRandom.randomUniformInt(100));
					pstmt.setInt(56,OraRandom.randomUniformInt(100));
					pstmt.setInt(57,OraRandom.randomUniformInt(100));
					pstmt.setInt(58,OraRandom.randomUniformInt(100));
					pstmt.setInt(59,OraRandom.randomUniformInt(100));
					pstmt.setInt(60,OraRandom.randomUniformInt(100));
					pstmt.setInt(61,OraRandom.randomUniformInt(100));
					pstmt.setInt(62,OraRandom.randomUniformInt(100));
					pstmt.setInt(63,OraRandom.randomUniformInt(100));
					pstmt.setInt(64,OraRandom.randomUniformInt(100));
					pstmt.setInt(65,OraRandom.randomUniformInt(100));
					pstmt.setInt(66,OraRandom.randomUniformInt(100));
					pstmt.setInt(67,OraRandom.randomUniformInt(100));
					pstmt.setInt(68,OraRandom.randomUniformInt(100));
					pstmt.setInt(69,OraRandom.randomUniformInt(100));
					pstmt.setInt(70,OraRandom.randomUniformInt(100));
					pstmt.setInt(71,OraRandom.randomUniformInt(100));
					pstmt.setInt(72,OraRandom.randomUniformInt(100));
					pstmt.setInt(73,OraRandom.randomUniformInt(100));
					pstmt.setInt(74,OraRandom.randomUniformInt(100));
					pstmt.setInt(75,OraRandom.randomUniformInt(100));
					pstmt.setInt(76,OraRandom.randomUniformInt(100));
					pstmt.setInt(77,OraRandom.randomUniformInt(100));
					pstmt.setInt(78,OraRandom.randomUniformInt(100));
					pstmt.setInt(79,OraRandom.randomUniformInt(100));
					pstmt.setInt(80,OraRandom.randomUniformInt(100));
					pstmt.setInt(81,OraRandom.randomUniformInt(100));
					pstmt.setInt(82,OraRandom.randomUniformInt(100));
					pstmt.setInt(83,OraRandom.randomUniformInt(100));
					pstmt.setInt(84,OraRandom.randomUniformInt(100));
					pstmt.setInt(85,OraRandom.randomUniformInt(100));
					pstmt.setInt(86,OraRandom.randomUniformInt(100));
					pstmt.setInt(87,OraRandom.randomUniformInt(100));
					pstmt.setInt(88,OraRandom.randomUniformInt(100));
					pstmt.setInt(89,OraRandom.randomUniformInt(100));
					pstmt.setInt(90,OraRandom.randomUniformInt(100));
					pstmt.setInt(91,OraRandom.randomUniformInt(100));
					pstmt.setInt(92,OraRandom.randomUniformInt(100));
					pstmt.setInt(93,OraRandom.randomUniformInt(100));
					pstmt.setInt(94,OraRandom.randomUniformInt(100));
					pstmt.setInt(95,OraRandom.randomUniformInt(100));
					pstmt.setInt(96,OraRandom.randomUniformInt(100));
					pstmt.setInt(97,OraRandom.randomUniformInt(100));
					pstmt.setInt(98,OraRandom.randomUniformInt(100));
					pstmt.setInt(99,OraRandom.randomUniformInt(100));
					pstmt.setInt(100,OraRandom.randomUniformInt(4000));
					pstmt.setInt(101,OraRandom.randomUniformInt(100));
					pstmt.setInt(102,OraRandom.randomUniformInt(100));
					pstmt.setInt(103,OraRandom.randomUniformInt(100));
					pstmt.setInt(104,OraRandom.randomUniformInt(100));
					pstmt.setInt(105,OraRandom.randomUniformInt(100));
					pstmt.setInt(106,OraRandom.randomUniformInt(100));
					pstmt.setInt(107,OraRandom.randomUniformInt(100));
					pstmt.setInt(108,OraRandom.randomUniformInt(100));
					pstmt.setInt(109,OraRandom.randomUniformInt(100));
					pstmt.setInt(110,OraRandom.randomUniformInt(100));
					pstmt.setInt(111,OraRandom.randomUniformInt(100));
					pstmt.setInt(112,OraRandom.randomUniformInt(100));
					pstmt.setInt(113,OraRandom.randomUniformInt(100));
					pstmt.setInt(114,OraRandom.randomUniformInt(100));
					pstmt.setInt(115,OraRandom.randomUniformInt(100));
					pstmt.setInt(116,OraRandom.randomUniformInt(100));
					pstmt.setInt(117,OraRandom.randomUniformInt(100));
					pstmt.setInt(118,OraRandom.randomUniformInt(100));
					pstmt.setInt(119,OraRandom.randomUniformInt(100));
					pstmt.setInt(120,OraRandom.randomUniformInt(100));
					pstmt.setInt(121,OraRandom.randomUniformInt(100));
					pstmt.setInt(122,OraRandom.randomUniformInt(100));
					pstmt.setInt(123,OraRandom.randomUniformInt(100));
					pstmt.setInt(124,OraRandom.randomUniformInt(100));
					pstmt.setInt(125,OraRandom.randomUniformInt(100));
					pstmt.setInt(126,OraRandom.randomUniformInt(100));
					pstmt.setInt(127,OraRandom.randomUniformInt(100));
					pstmt.setInt(128,OraRandom.randomUniformInt(100));
					pstmt.setInt(129,OraRandom.randomUniformInt(100));
					pstmt.setInt(130,OraRandom.randomUniformInt(100));
					pstmt.setInt(131,OraRandom.randomUniformInt(100));
					pstmt.setInt(132,OraRandom.randomUniformInt(100));
					pstmt.setInt(133,OraRandom.randomUniformInt(100));
					pstmt.setInt(134,OraRandom.randomUniformInt(100));
					pstmt.setInt(135,OraRandom.randomUniformInt(100));
					pstmt.setInt(136,OraRandom.randomUniformInt(100));
					pstmt.setInt(137,OraRandom.randomUniformInt(100));
					pstmt.setInt(138,OraRandom.randomUniformInt(100));
					pstmt.setInt(139,OraRandom.randomUniformInt(100));
					pstmt.setInt(140,OraRandom.randomUniformInt(100));
					pstmt.setInt(141,OraRandom.randomUniformInt(100));
					pstmt.setInt(142,OraRandom.randomUniformInt(100));
					pstmt.setInt(143,OraRandom.randomUniformInt(100));
					pstmt.setInt(144,OraRandom.randomUniformInt(100));
					pstmt.setInt(145,OraRandom.randomUniformInt(100));
					pstmt.setInt(146,OraRandom.randomUniformInt(100));
					pstmt.setInt(147,OraRandom.randomUniformInt(100));
					pstmt.setInt(148,OraRandom.randomUniformInt(100));
					pstmt.setInt(149,OraRandom.randomUniformInt(100));
					pstmt.setInt(150,OraRandom.randomUniformInt(100));
					pstmt.setInt(151,OraRandom.randomUniformInt(100));
					pstmt.setInt(152,OraRandom.randomUniformInt(100));
					pstmt.setInt(153,OraRandom.randomUniformInt(100));
					pstmt.setInt(154,OraRandom.randomUniformInt(100));
					pstmt.setInt(155,OraRandom.randomUniformInt(100));
					pstmt.setInt(156,OraRandom.randomUniformInt(100));
					pstmt.setInt(157,OraRandom.randomUniformInt(100));
					pstmt.setInt(158,OraRandom.randomUniformInt(100));
					pstmt.setInt(159,OraRandom.randomUniformInt(100));
					pstmt.setInt(160,OraRandom.randomUniformInt(100));
					pstmt.setInt(161,OraRandom.randomUniformInt(100));
					pstmt.setInt(162,OraRandom.randomUniformInt(100));
					pstmt.setInt(163,OraRandom.randomUniformInt(100));
					pstmt.setInt(164,OraRandom.randomUniformInt(100));
					pstmt.setInt(165,OraRandom.randomUniformInt(100));
					pstmt.setInt(166,OraRandom.randomUniformInt(100));
					pstmt.setInt(167,OraRandom.randomUniformInt(100));
					pstmt.setInt(168,OraRandom.randomUniformInt(100));
					pstmt.setInt(169,OraRandom.randomUniformInt(100));
					pstmt.setInt(170,OraRandom.randomUniformInt(100));
					pstmt.setInt(171,OraRandom.randomUniformInt(100));
					pstmt.setInt(172,OraRandom.randomUniformInt(100));
					pstmt.setInt(173,OraRandom.randomUniformInt(100));
					pstmt.setInt(174,OraRandom.randomUniformInt(100));
					pstmt.setInt(175,OraRandom.randomUniformInt(100));
					pstmt.setInt(176,OraRandom.randomUniformInt(100));
					pstmt.setInt(177,OraRandom.randomUniformInt(100));
					pstmt.setInt(178,OraRandom.randomUniformInt(100));
					pstmt.setInt(179,OraRandom.randomUniformInt(100));
					pstmt.setInt(180,OraRandom.randomUniformInt(100));
					pstmt.setInt(181,OraRandom.randomUniformInt(100));
					pstmt.setInt(182,OraRandom.randomUniformInt(100));
					pstmt.setInt(183,OraRandom.randomUniformInt(100));
					pstmt.setInt(184,OraRandom.randomUniformInt(100));
					pstmt.setInt(185,OraRandom.randomUniformInt(100));
					pstmt.setInt(186,OraRandom.randomUniformInt(100));
					pstmt.setInt(187,OraRandom.randomUniformInt(100));
					pstmt.setInt(188,OraRandom.randomUniformInt(100));
					pstmt.setInt(189,OraRandom.randomUniformInt(100));
					pstmt.setInt(190,OraRandom.randomUniformInt(100));
					pstmt.setInt(191,OraRandom.randomUniformInt(100));
					pstmt.setInt(192,OraRandom.randomUniformInt(100));
					pstmt.setInt(193,OraRandom.randomUniformInt(100));
					pstmt.setInt(194,OraRandom.randomUniformInt(100));
					pstmt.setInt(195,OraRandom.randomUniformInt(100));
					pstmt.setInt(196,OraRandom.randomUniformInt(100));
					pstmt.setInt(197,OraRandom.randomUniformInt(100));
					pstmt.setInt(198,OraRandom.randomUniformInt(100));
					pstmt.setInt(199,OraRandom.randomUniformInt(100));
					pstmt.setInt(200,OraRandom.randomUniformInt(100));
					pstmt.setInt(201,OraRandom.randomUniformInt(100));
					pstmt.setInt(202,OraRandom.randomUniformInt(100));
					pstmt.setInt(203,OraRandom.randomUniformInt(100));
					pstmt.setInt(204,OraRandom.randomUniformInt(100));
					pstmt.setInt(205,OraRandom.randomUniformInt(100));
					pstmt.setInt(206,OraRandom.randomUniformInt(100));
					pstmt.setInt(207,OraRandom.randomUniformInt(100));
					pstmt.setInt(208,OraRandom.randomUniformInt(100));
					pstmt.setInt(209,OraRandom.randomUniformInt(100));
					pstmt.setInt(210,OraRandom.randomUniformInt(100));
					pstmt.setInt(211,OraRandom.randomUniformInt(100));
					pstmt.setInt(212,OraRandom.randomUniformInt(100));
					pstmt.setInt(213,OraRandom.randomUniformInt(100));
					pstmt.setInt(214,OraRandom.randomUniformInt(100));
					pstmt.setInt(215,OraRandom.randomUniformInt(100));
					pstmt.setInt(216,OraRandom.randomUniformInt(100));
					pstmt.setInt(217,OraRandom.randomUniformInt(100));
					pstmt.setInt(218,OraRandom.randomUniformInt(100));
					pstmt.setInt(219,OraRandom.randomUniformInt(100));
					pstmt.setInt(220,OraRandom.randomUniformInt(100));
					pstmt.setInt(221,OraRandom.randomUniformInt(100));
					pstmt.setInt(222,OraRandom.randomUniformInt(100));
					pstmt.setInt(223,OraRandom.randomUniformInt(100));
					pstmt.setInt(224,OraRandom.randomUniformInt(100));
					pstmt.setInt(225,OraRandom.randomUniformInt(100));
					pstmt.setInt(226,OraRandom.randomUniformInt(100));
					pstmt.setInt(227,OraRandom.randomUniformInt(100));
					pstmt.setInt(228,OraRandom.randomUniformInt(100));
					pstmt.setInt(229,OraRandom.randomUniformInt(100));
					pstmt.setInt(230,OraRandom.randomUniformInt(100));
					pstmt.setInt(231,OraRandom.randomUniformInt(100));
					pstmt.setInt(232,OraRandom.randomUniformInt(100));
					pstmt.setInt(233,OraRandom.randomUniformInt(100));
					pstmt.setInt(234,OraRandom.randomUniformInt(100));
					pstmt.setInt(235,OraRandom.randomUniformInt(100));
					pstmt.setInt(236,OraRandom.randomUniformInt(100));

					pstmt.addBatch();
					if (i%10000 == 0) {
						pstmt.executeBatch();
						System.out.println("Inserted into Intrablock --> " + i +" rows");
					}
					i++;
				}
				pstmt.executeBatch();
				pstmt.close();
				oraCon.close();
				System.out.println("Load Complete");
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	
	
	}
}
