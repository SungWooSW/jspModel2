<%@page import="dto.BbsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%

	String message = (String)request.getAttribute("message");
	if(message != null && message.equals("") == false){
		if(message.equals("MEMBER_YES")){
			%>
			<script type="text/javascript">
			alert("성공적으로 가입되었습니다.");
			location.href = "member?param=login";
			</script>
			<%
		} else {
			%>
			<script type="text/javascript">
			alert("가입되지 않았습니다. 다시 기입해 주십시오");
			location.href = "member?param=regi";
			</script>
			<%
		}
	}	
	
String bbswrite = (String)request.getAttribute("bbswrite");	
if(bbswrite != null && bbswrite.equals("") == false){
	if(bbswrite.equals("BBS_ADD_OK")){
		%>
		<script type="text/javascript">
		alert("성공적으로 작성되었습니다.");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	} else {
		%>
		<script type="text/javascript">
		alert("다시 작성해주십시오.");
		location.href = "bbs?param=bbswrite";
		</script>
		<%
	}
}

String answer = (String)request.getAttribute("answer");
if(answer != null && !answer.equals("")){
	if(answer.equals("BBS_ANSWER_OK")){
		%>
		<script type="text/javascript">
		alert("답글이 성공적으로 작성되었습니다");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
		alert("답글을 다시 작성해 주십시오");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}	
}

String updateBbs = (String)request.getAttribute("updateBbs");
if(updateBbs != null && !updateBbs.equals("")){
	if(updateBbs.equals("BBS_UPDATE_OK")){
		%>
		<script type="text/javascript">
		alert("글이 성공적으로 수정되었습니다");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
		alert("글을 다시 작성해 주십시오");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}	
}

String deleteBbs = (String)request.getAttribute("deleteBbs");
if(deleteBbs != null && !deleteBbs.equals("")){
	if(deleteBbs.equals("BBS_DELETE_OK")){
		%>
		<script type="text/javascript">
		alert("글이 성공적으로 삭제되었습니다");
		location.href = "bbs?param=bbslist";
		</script>
		<%
	}
	else{
		%>
		<script type="text/javascript">
		alert("삭제할 글을 다시 선택해주십시오");
		location.href = "bbs?param=bbslist;
		</script>
		<%
	}	
}



%>
	
	








