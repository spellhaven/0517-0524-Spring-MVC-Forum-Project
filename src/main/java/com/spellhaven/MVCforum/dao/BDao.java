package com.spellhaven.MVCforum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.spellhaven.MVCforum.dto.BDto;

public class BDao {
	
	DataSource dataSource;

	public BDao() {
		super();
		
		try {			
			// 헐! 이 두 라인은 뭔말임! (몰라. 모르면서 쓰는 거지 뭐🥰)
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");	
		
		} catch (NamingException e) {
			e.printStackTrace();
		}	
		
	}
	
	
	// 글 목록 보여 주는 놈, list()
	public ArrayList<BDto> list() { 
		
		// 왜 반환 타입이 ArrayList<BDto>인가? 글 하나하나가 BDto라는 배열이고, 글 목록은 그거의 리스트이기 때문이다.
		// JSP로 MVC 패턴 게시판 만들기 할 때도 하신 얘기임, ㅋ,
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 글 목록을 볼 거니까 SELECT문. ResultSet으로 반환값을 받아야 해.
		
		try { // DB에 데이터 넣는 건 나름 위험하니까 try-catch 해야 한디.
			
			conn = dataSource.getConnection();
			String query = "SELECT * FROM mvc_board ORDER BY bgroup DESC, bstep ASC";
			// ORDER BY bgroup DESC, bstep ASC는 무슨 말이냐? 이 게시판은 글뿐만 아니라 댓글도 '정확한 순서대로' 불러와야 하기 때문에 그렇다.
			// 왜 그런지 모르겠으면 원노트 22.05.17 화 - Oracle Express 11g, SQL Plus 설치 (Spring 게시판 만들기 시작!!!) 페이지 보셈, ㅋ
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
				
			while(rs.next()) { // 존재하는 글을 다 긁어온다는 뜻의 while문.
				
				// "필드명" 틀리지 마, ㅋ, 오라클 DB 안의 테이블이랑 딱딱 맞춰.
				int bId = rs.getInt("bid");
				String bName = rs.getString("bname");
				String bTitle = rs.getString("btitle");
				String bContent = rs.getString("bcontent");
				Timestamp bDate = rs.getTimestamp("bdate"); // 이 Timestamp는 java.sql이야... 크킄... 다른 소속 Timestamp 아니여...
				int bHit = rs.getInt("bhit");
				int bGroup = rs.getInt("bgroup");
				int bStep = rs.getInt("bstep");
				int bIndent = rs.getInt("bindent");
				
				// 값이 9개나 되는데 세터로 어떻게 해 귀찮게. BDto 생성자 만들어 두길 잘 했다.
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	
		return dtos;
	}

	
	// 글 써 주는 놈, write()
	public void write(String bname, String btitle, String bcontent) { 
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try { // DB에 데이터 넣는 건 나름 위험하니까 try-catch 해야 한디.
			
			conn = dataSource.getConnection();
			String query = "INSERT INTO mvc_board(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent) values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
			// 대충 쿼리문에 대한 설명) bid, bgroup은 SQL 시퀀스를 쓰고 글쓴이, 글 제목, 글 내용은 뭐가 들어올지 모르니 ?, ?, ?이고... 이런 얘기다.
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bname); // 코딩할 때 인덱스는 0부터 시작하는데 setString은 1부터 시작하는 거 킹받음;;
			pstmt.setString(2, btitle); // 저번에 얘들 순서 틀려서 에러 났던 거 기억남? 추억이다 그것도.
			pstmt.setString(3, bcontent);
			
			// 아깐 pstmt.executeUpdate(query)라고 해서 에러가 났었다. 이미 prepareStatement에서 쿼리 준비해 줬기 때문에 여기서 또 하면 중복이디. 에러 난다, ㅋ. (심지어 아무 상관 없어 보이는 "not all variables are bound" 에러가...)
			pstmt.executeUpdate(); // 데이터 삽입에 성공하면 1 반환하는 형식으로 만들 수도 있다. 그러면 public int write()로 해서 리턴값도 설정해야지, ㅋ
			
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}


	public BDto content_view(String cid) { // DB의 bid랑 이름이 겹쳐서 헷갈릴까 봐 바꿈, ㅋ.
	// 왜 리턴형이 BDto인가? 글 제목을 누르면 그 글 '하나만' 보여 줘야 하니까. (잘 모르겠으면 항복해라, ㅋ)
		
		upHit(cid); // 얘는 해당 글이 호출될 때마다 조회수 1씩 늘려 주는 놈이야. 바로 이 BDao 하단에 있다.
		
		BDto dto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try { // DB에 데이터 넣는 건 나름 위험하니까 try-catch 해야 한디.
			
			conn = dataSource.getConnection();
			String query = "SELECT * FROM mvc_board WHERE bid = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cid);
			// pstmt.setInt(1, Integer.parseInt(cid)); // 이래야지 들어온 cid가 숫자로 바뀌는데, 우리가 실수로 DB에서 bid를 NUMBER로 안 하고 VARCHAR로 해 버렸다 ㅋ 원랜 이렇게 해야 한다. ㅋ.
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// "필드명" 틀리지 마, ㅋ, 오라클 DB 테이블이랑 딱딱 맞춰.
				int bId = rs.getInt("bid");
				String bName = rs.getString("bname");
				String bTitle = rs.getString("btitle");
				String bContent = rs.getString("bcontent");
				Timestamp bDate = rs.getTimestamp("bdate"); 
				int bHit = rs.getInt("bhit");
				int bGroup = rs.getInt("bgroup");
				int bStep = rs.getInt("bstep");
				int bIndent = rs.getInt("bindent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
		
	}

	
	// 글 수정해 주는 놈
	public void modify(String bid, String bname, String btitle, String bcontent) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try { // DB에 데이터 넣는 건 나름 위험하니까 try-catch 해야 한디.
			
			conn = dataSource.getConnection();
			String query = "UPDATE mvc_board SET bname = ?, btitle = ?, bcontent = ? where bid = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bname); 
			pstmt.setString(2, btitle); 
			pstmt.setString(3, bcontent);
			pstmt.setString(4, bid); // bid는 1번이 아니라 4번이야. setString은 쿼리의 ?를 순서대로 채워 주는 거임;; modify의 파라미터 순서와는 상관 x
			
			pstmt.executeUpdate();
			
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}


	// 쉿. 그 놈, 담가줄게.
	public void delete(String bid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try { // DB에 데이터 넣는 건 나름 위험하니까 try-catch 해야 한디.
			
			conn = dataSource.getConnection();
			String query = "DELETE FROM mvc_board where bid=?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bid); 
			pstmt.executeUpdate();
			
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	// 글 누를 때마다(content_view가 실행될 때마다) 해당 글 조회수 늘려 주는 메서드... 인데 이름이 모 가상화폐 회사 같아서 기분이 썩 좃치안내
	public void upHit(String bid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try { // DB에 데이터 넣는 건 나름 위험하니까 try-catch 해야 한디.
			
			conn = dataSource.getConnection();
			String query = "UPDATE mvc_board SET bhit = bhit+1 where bid = ?";
			// 이 쿼리문을 쓰는 덴 여러 방법이 있다. 근데 bhit 가져왔다가 다시 bhit+1 넣어 주는 건 너무 귀찮으니까, 걍 야! 너 +1 해!! (네 알겠습니다.) 하고 수식으로 만들었다.
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bid); 
			pstmt.executeUpdate();
			
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}


	public BDto reply_view(String bid) {
		
		BDto dto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try { // DB에 데이터 넣는 건 나름 위험하니까 try-catch 해야 한디.
			
			conn = dataSource.getConnection();
			String query = "SELECT * FROM mvc_board WHERE bid = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cid);
			// pstmt.setInt(1, Integer.parseInt(cid)); // 이래야지 들어온 cid가 숫자로 바뀌는데, 우리가 실수로 DB에서 bid를 NUMBER로 안 하고 VARCHAR로 해 버렸다 ㅋ 원랜 이렇게 해야 한다. ㅋ.
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// "필드명" 틀리지 마, ㅋ, 오라클 DB 테이블이랑 딱딱 맞춰.
				int bId = rs.getInt("bid");
				String bName = rs.getString("bname");
				String bTitle = rs.getString("btitle");
				String bContent = rs.getString("bcontent");
				Timestamp bDate = rs.getTimestamp("bdate"); 
				int bHit = rs.getInt("bhit");
				int bGroup = rs.getInt("bgroup");
				int bStep = rs.getInt("bstep");
				int bIndent = rs.getInt("bindent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;		
	}
	
	
}





















