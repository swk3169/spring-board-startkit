package hu.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import hu.admin.board.BoardGroupVO;

@Service
public class BoardSvc {

	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private DataSourceTransactionManager txManager;
	
	static final Logger LOGGER = LoggerFactory.getLogger(BoardSvc.class);
	
	 /** 
     * 게시판 정보 (그룹).
     */
	public BoardGroupVO selectBoardGroupOne4Used(String param) {
		return sqlSession.selectOne("selectBoardGroupOne4Used", param);
	}
	
	/** ------------------------------------------
     * 게시판.
     */
	
	public Integer selectBoardCount(BoardSearchVO param) {
		return sqlSession.selectOne("selectBoardCount", param);
	}
	
	public List<?> selectBoardList(BoardSearchVO param){
		return sqlSession.selectList("selectBoardList",param);
	}
	
	public List<?> selectNoticeList(BoardSearchVO param) {
		return sqlSession.selectList("selectNoticeList", param);
	}
	
	
}
