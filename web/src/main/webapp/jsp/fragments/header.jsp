<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>Header</title>
<%@ include file="../include.jsp"%>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">
                        <fmt:message key="frag.header.name" />
                    </a>
				</div>
				<div>
					<ul class="nav navbar-nav">
						<li>
                            <a href="controller?command=page_import" >
                                <fmt:message key="frag.header.import" />
                            </a>
                        </li>
						<li>
                            <a href="controller?command=view_data&page=1">
                                <fmt:message key="frag.header.data" />
                            </a>
                        </li>
					</ul>
				</div>
			</div>
		</nav>
	</div>
</body>
</html>