package hu.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import hu.admin.board.BoardGroupSvc;
import hu.admin.board.BoardGroupVO;
import hu.etc.EtcSvc;

@Controller
public class BoardCtr {
	@Autowired
	private BoardSvc boardSvc;
	
	@Autowired
	private BoardGroupSvc boardGroupSvc;
	
	@Autowired
	private EtcSvc etcSvc;
	
	static final Logger LOGGER = LoggerFactory.getLogger(BoardCtr.class);
	
    /**
     * 게시판 리스트.
     */
	@RequestMapping(value = "/boardList")
	public String boardList(HttpServletRequest request, BoardSearchVO searchVO, ModelMap modelMap) {
		String globalKeyword = request.getParameter("globalKeyword"); // jsp 왼쪽 사이드 바으로부터 찾기
		if(globalKeyword!=null & !"".contentEquals(globalKeyword)) {
			searchVO.setSearchKeyword(globalKeyword);
		}
		
		String userno = request.getSession().getAttribute("userno").toString();
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		modelMap.addAttribute("alertcount", alertcount);
		
		if(searchVO.getBgno() != null && !"".equals(searchVO.getBgno())) {
			BoardGroupVO bgInfo = boardSvc.selectBoardGroupOne4Used(searchVO.getBgno());
			if(bgInfo == null) {
				return "board/BoardGroupFail";
			}
			modelMap.addAttribute("bgInfo", bgInfo);
		}
		
		List<?> noticelist = boardSvc.selectNoticeList(searchVO);
		
		searchVO.pageCalculate(boardSvc.selectBoardCount(searchVO)); //startRow, endRow
		List<?> listview = boardSvc.selectBoardList(searchVO);
		
		modelMap.addAttribute("searchVO", searchVO);
		modelMap.addAttribute("listview", listview);
		modelMap.addAttribute("noticelist", noticelist);
		
		if(searchVO.getBgno() == null || "".equals(searchVO.getBgno())) {
			return "board/BoardListAll"; //?????
		}
		
		return "board/BoardList";
	}
	
}
