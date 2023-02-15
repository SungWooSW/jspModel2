<%@page import="util.Utility"%>
<%@page import="java.util.List"%>
<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
	MemberDto login = (MemberDto)session.getAttribute("login"); //session.getAttribute("login")의 리턴값은 Object이므로 cast(형변환)!
	if(login == null){ // 세션값이 없다. 즉, 세션이 만료된 경우가 포함된다.
		%>
		<script>
		alert('로그인 해 주십시오');
		location.href = "login.jsp";
		</script>
		<%
	}	
%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
// 검색 버튼 누르고, BbsController로 이동했다가 getBbsSearchList메서드를 통해 list 생성후
// 다시 bbslist.jsp로 list를 넘겨줌
List<BbsDto> list = (List<BbsDto>)request.getAttribute("bbslist");
int pageBbs = (Integer)request.getAttribute("pageBbs");
int pageNumber = (Integer)request.getAttribute("pageNumber");
String choice = (String)request.getAttribute("choice");
String search = (String)request.getAttribute("search");

%>



<h1>게시판</h1>

<div align="center">

<table border="1">
<col width="70"><col width="600"><col width="100"><col width="150">
<thead>
<tr>
	<th>번호</th>
	<th>제목</th>
	<th>조회수</th>
	<th>작성자</th>
</tr>
</thead>
<tbody>

<%
// dao.getBbsPageList(choice, search, pageNumber)에서 list 반환 후
if(list == null || list.size() == 0){
	%>
	<tr>
		<td colspan="4">작성된 글이 없습니다</td>
	</tr>
	<%
}else{
	
	for(int i = 0;i < list.size(); i++)
	{
		BbsDto dto = list.get(i);
		%>
		<tr>
			<th><%=i + 1 %></th>
			
			<%
			if(dto.getDel() == 0){
				%>			
				<td>
					<%=Utility.arrow(dto.getDepth()) %>
					<a href="bbs?param=bbsdetail&seq=<%=dto.getSeq() %>">
						<%=dto.getTitle() %>
					</a>
				</td>			
				<%
			}else if(dto.getDel() == 1){
				%>
				<td>
					<%=Utility.arrow(dto.getDepth()) %>
					<font color="#ff0000">*** 이 글은 작성자에 의해서 삭제되었습니다 ***</font>	
				</td>
				<%
			}	
			%>
			<%--<td><%= dto.getRef() %>-<%= dto.getStep() %>-<%= dto.getDepth() %></td>--%>
			<td><%=dto.getReadcount() %></td>
			<td><%=dto.getId() %></td>			
		</tr>
		<%
	}
}
%>


</tbody>

</table>

<br><br>

<%
	// 페이징(getAllBbs 메서드 실행, pageBbs 변수 선언 후 이어서 진행!)
	for(int i = 0; i < pageBbs; i++) {
		if(pageNumber == i){ 		// 페이지 넘버가 현재 페이지일 경우
			%>
			<span style="font-size: 15pt; color: #0000ff; font-weight: bold;">
				<%=i+1 %>
			</span>
			<% 
		} else {                    // 그 밖의 페이지
			%>
			<a href="#none" title="<%=i+1 %>페이지" onclick="goPage(<%=i %>)"
				style="font-size: 15pt; color: #000; font-weight: bold; text-decoration: none;">
				[<%=i+1 %>]
			</a>
			<% 						
		}
	}

%>


<select id="choice">
	<option value="">검색</option>
	<option value="title">제목</option>
	<option value="content">내용</option>
	<option value="writer">작성자</option>
</select>

<input type="text" id="search" value="<%=search %>">  <!-- 검색 키워드(search) 유지 -->

<button type="button" onclick="searchBtn()">검색</button>

<br><br>
<a href="bbs?param=bbswrite">글 쓰기</a>

</div>

<script type="text/javascript">
//검색 항목(choice) 유지하기 위한 코드
let search = "<%=search %>";
if(search != ""){
	let obj = document.getElementById("choice");
	obj.value = "<%=choice %>"; 
	obj.setAttribute("selected", "selected");
}

function searchBtn(){
	let choice = document.getElementById('choice').value;
	let search = document.getElementById('search').value;
	/*
	if(choice == ""){ 
		alert("카테고리를 선택해 주십시오");
		return;
	}
	if(search.trim() == ""){
		alert("검색어를 입력해 주십시오");
		return;
	}
	*/
	location.href = "bbs?param=bbslist&choice=" + choice + "&search=" + search;
	
}

function goPage( pageNumber ) {
	let choice = document.getElementById('choice').value;
	let search = document.getElementById('search').value;
	
	location.href = "bbs?param=bbslist&choice=" + choice + "&search=" + search + "&pageNumber=" + pageNumber;	
}

</script>








</body>
</html>