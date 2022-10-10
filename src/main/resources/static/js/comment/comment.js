function getComments(boardId) {
    $.ajax({
        url: "/api/comment/" + boardId,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            // location.href="/view/board/"+data.result[0].boardId;

           createElement(data);

        },
        error: function (request, status, error) {
            let err = JSON.parse(request.responseText);
            alert(err.resultCode);
        }
    });
}

function postComment() {
    let boardId = document.getElementById('boardId').value;
    let userName = document.getElementById('userName').value.trim();
    let userPassword = document.getElementById('userPassword').value.trim();
    let comment = document.getElementById('comment').value.trim();

    if (postCommentValid(userName, userPassword, comment)) {
        $.ajax({
            url: "/api/comment",
            type: "POST",
            dataType: "json",
            data: JSON.stringify({"boardId" : boardId, "commentUserName" : userName, "commentPassword" : userPassword, "comment" : comment}),
            contentType: "application/json",
            success: function (data) {
                getComments(data.result);
                document.getElementById('userName').value = '';
                document.getElementById('userPassword').value = '';
                document.getElementById('comment').value = '';
            },
            error: function (request, status, error) {
                // alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                let err = JSON.parse(request.responseText);
                alert(err.resultCode);
            }
        });
    }
}

function reCommentForm(commentId, depth) {
    let updateDiv = document.getElementById('updateDiv' + commentId);
    let children = updateDiv.children;
    if (children.length === 0) {
        const tagList = `
                <div >
                    <label for="userName">작성자</label>
                    <input type="text" id="reCommentUserName" name="userName" value="" >
                    <label for="userPassword">비밀번호</label>
                    <input type="password" id="reCommentPassword" name="userPassword" value="">
                </div>
                <div class="col-md-9 col-lg-8">
                    <label for="comment" hidden>댓글</label>
                    <textarea class="form-control" id="reCommentComment" placeholder="답글 쓰기.." rows="3" value="" name="comment" required></textarea>
                </div>
                <div class="col-md-3 col-lg-5">
                    <label for="comment-submit" hidden>댓글 쓰기</label>
                    <button class="btn btn-primary" id="comment-submit" type="button" onclick="reComment(${commentId})">답글 달기</button>
                </div> 
            `;

        updateDiv.innerHTML = tagList;
    } else {
        while ( updateDiv.hasChildNodes() )
        {
            updateDiv.removeChild( updateDiv.firstChild );
        }
    }
}

function reComment(commentId) {
    let boardId = document.getElementById('boardId').value;
    let userName = document.getElementById('reCommentUserName').value.trim();
    let userPassword = document.getElementById('reCommentPassword').value.trim();
    let comment = document.getElementById('reCommentComment').value.trim();

    if (reCommentValid(userName, userPassword, comment)) {
        $.ajax({
            url: "/api/comment/" + commentId,
            type: "POST",
            dataType: "json",
            data: JSON.stringify({"boardId" : boardId, "commentId" : commentId, "commentUserName" : userName,"commentPassword" : userPassword, "comment" : comment, "groupId" : commentId}),
            contentType: "application/json",
            success: function (data) {
                getComments(data.result);
                document.getElementById('userName').value = '';
                document.getElementById('userPassword').value = '';
                document.getElementById('comment').value = '';
            },
            error: function (request, status, error) {
                let err = JSON.parse(request.responseText);
                alert(err.resultCode);
            }
        });
    }
}

function updateFormComment(userName, comment, commentId) {
    let updateDiv = document.getElementById('updateDiv' + commentId);
    let children = updateDiv.children;
    if (children.length === 0) {
        const tagList = `
                <div >
                    <label for="userName">작성자</label>
                    <input type="text" id="updateUserName" name="userName" value="${userName}" readonly>
                    <label for="userPassword">비밀번호</label>
                    <input type="password" id="updateUserPassword" name="userPassword" value="">
                </div>
                <div class="col-md-9 col-lg-8">
                    <label for="comment" hidden>댓글</label>
                    <textarea class="form-control" id="updateComment" placeholder="댓글 쓰기.." rows="3" value="" name="comment" required>${comment}</textarea>
                </div>
                <div class="col-md-3 col-lg-5">
                    <label for="comment-submit" hidden>댓글 쓰기</label>
                    <button class="btn btn-success" id="comment-submit" type="button" onclick="updateComment(${commentId})">댓글 수정</button>
                </div> 
            `;

        updateDiv.innerHTML = tagList;
    } else {
        while ( updateDiv.hasChildNodes() )
        {
            updateDiv.removeChild( updateDiv.firstChild );
        }
    }
}

function updateComment(commentId) {
    let boardId = document.getElementById('boardId').value;
    // let userName = document.getElementById('updateUserName').value.trim();
    let userPassword = document.getElementById('updateUserPassword').value.trim();
    let comment = document.getElementById('updateComment').value.trim();

    if (updateCommentValid(userPassword, comment)) {
        $.ajax({
            url: "/api/comment/" + commentId,
            type: "PUT",
            dataType: "json",
            data: JSON.stringify({"boardId" : boardId, "commentId" : commentId, "commentPassword" : userPassword, "comment" : comment}),
            contentType: "application/json",
            success: function (data) {
                getComments(data.result);
                document.getElementById('userName').value = '';
                document.getElementById('userPassword').value = '';
                document.getElementById('comment').value = '';
            },
            error: function (request, status, error) {
                let err = JSON.parse(request.responseText);
                alert(err.resultMessage);
            }
        });
    }
}

function deleteComment(commentId, boardId, page) {
    let inputPassword = prompt('비밀번호를 입력하세요');
    inputPassword.trim();
    $.ajax({
        url: "/api/comment/" + commentId,
        type: "DELETE",
        dataType: "json",
        data: JSON.stringify({"commentId" : commentId, "boardId" : boardId, "commentPassword" : inputPassword}),
        contentType: "application/json",
        success: function (data) {
            for (let i = 0; i < data.result.length; i++) {
                let liId = 'li' + data.result[i];
                document.getElementById(liId).remove();
            }
        },
        error: function (request, status, error) {
            let err = JSON.parse(request.responseText);
            alert(err.resultMessage);
        }
    });
}

function postCommentValid(userName, userPassword, comment) {
    if (!userName) {
        alert("작성자를 채워주세요.")
        document.getElementById('userName').focus();
        return false;
    }
    if (userName.length > 10) {
        alert("작성자는 10자 이하만 채워주세요.")
        document.getElementById('userName').focus();
        return false;
    }
    if (!userPassword) {
        alert("비밀번호를 채워주세요");
        document.getElementById('userPassword').focus();
        return false;
    }
    if (userPassword.length > 20) {
        alert("비밀번호는 20자 이하만 가능합니다.");
        document.getElementById('userPassword').focus();
        return false;
    }
    if (!comment) {
        alert("댓글 내용을 채워주세요");
        document.getElementById('comment').focus();
        return false;
    }
    if (comment.length > 100) {
        alert("댓글은 100자만 가능합니다");
        document.getElementById('comment').focus();
        return false;
    }
    let check = /^[0-9]+$/;
    if (userPassword.length > 20 || !check.test(userPassword)) {
        alert("비밀번호는 20자이하 숫자만 가능합니다.")
        return false;
    }
    return true;
}
function updateCommentValid(userPassword, comment) {
    if (!userPassword) {
        alert("수정 비밀번호를 채워주세요");
        document.getElementById('updateUserPassword').focus();
        return false;
    }
    if (!comment) {
        alert("수정 댓글 내용을 채워주세요");
        document.getElementById('updateComment').focus();
        return false;
    }
    if (userPassword.length > 20) {
        alert("수정 비밀번호는 20자 이하만 가능합니다");
        document.getElementById('updateUserPassword').focus();
        return false;
    }
    if (comment.length > 100) {
        alert("수정 댓글 내용은 100자 이하만 가능합니다.");
        document.getElementById('updateComment').focus();
        return false;
    }
    let check = /^[0-9]+$/;
    if (userPassword.length > 20 || !check.test(userPassword)) {
        alert("비밀번호는 20자이하 숫자만 가능합니다.")
        document.getElementById('updateUserPassword').focus();
        return false;
    }
    return true;
}

function reCommentValid(userName, userPassword, comment){
    if (!userName) {
        alert("작성자를 채워주세요");
        document.getElementById('reCommentUserName').focus();
        return false;
    }
    if (userName.length > 10) {
        alert("작성자는 10자 이하만 가능합니다.");
        document.getElementById('reCommentUserName').focus();
        return false;
    }
    if (!userPassword) {
        alert("비밀번호를 채워주세요");
        document.getElementById('reCommentPassword').focus();
        return false;
    }
    if (userPassword.length > 20) {
        alert("비밀번호는 20자 이하만 가능합니다.");
        document.getElementById('reCommentPassword').focus();
        return false;
    }
    if (!comment) {
        alert("댓글 내용을 채워주세요");
        document.getElementById('reCommentComment').focus();
        return false;
    }
    if (comment.length > 100) {
        alert("댓글 내용은 100자 이하만 가능합니다.");
        document.getElementById('reCommentComment').focus();
        return false;
    }
    let check = /^[0-9]+$/;
    if (userPassword.length > 20 || !check.test(userPassword)) {
        alert("비밀번호는 20자이하 숫자만 가능합니다.")
        return false;
    }
    return true;
}

function createElement(data) {
    let ul = document.getElementById('board-comments');
    while ( ul.hasChildNodes() )
    {
        // console.log("while in ul.hasChildNodes(): "+ ul.hasChildNodes());
        ul.removeChild( ul.firstChild );
    }
    for (let i = 0; i < data.result.length; i++) {
        let li = document.createElement("li");
        let liId = 'li' + data.result[i].commentId;
        li.setAttribute('id', liId);
        ul.appendChild(li);

        let liById = document.getElementById(liId);
        let rowDiv = document.createElement('div');
        rowDiv.setAttribute('class', 'row');
        liById.append(rowDiv);

        let commentDiv1 = document.createElement("div");
        commentDiv1.setAttribute("class", "col-md-10 col-lg-8");
        rowDiv.append(commentDiv1);


        let strong1 = document.createElement("strong");
        commentDiv1.append(strong1);
        if (data.result[i].step > 0) {
            let arrow = '';
            for (let j = 0; j < data.result[i].step; j++) {
                arrow += "&rarr;";
            }
            let commentIndex = i + 1;
            strong1.innerHTML = arrow + commentIndex + "</br>";
        } else {
            strong1.innerText = i +1 + "\n";
        }


        let strong2 = document.createElement("strong");
        commentDiv1.append(strong2);
        if (data.result[i].step > 0) {
            let nbsp = '';
            for (let j = 0; j < data.result[i].step; j++) {
                nbsp += "&nbsp;";
            }
            strong2.innerHTML = nbsp + "작성자 : " + data.result[i].commentUserName ;
        } else {
            strong2.innerText = "작성자 : " + data.result[i].commentUserName ;
        }


        let p = document.createElement('p');
        commentDiv1.append(p);
        if (data.result[i].step > 0) {
            let nbsp = '';
            for (let j = 0; j < data.result[i].step; j++) {
                nbsp += "&nbsp;";
            }
            p.innerHTML = nbsp + "댓글 내용 : " + data.result[i].comment;
        } else {
            p.innerText = "댓글 내용 : " + data.result[i].comment;
        }

        let buttonDiv = document.createElement('div');
        buttonDiv.setAttribute('class', 'col-md-3 col-lg-4');
        rowDiv.append(buttonDiv);

        let reCommentButton = document.createElement('button');
        reCommentButton.setAttribute('class', 'btn btn-outline-primary');
        reCommentButton.setAttribute('onclick', 'reCommentForm('+ data.result[i].commentId+')');
        reCommentButton.innerHTML = "답글";
        buttonDiv.append(reCommentButton);

        let updateButton = document.createElement('button');
        updateButton.setAttribute('class', 'btn btn-outline-success');
        updateButton.setAttribute('onclick', "updateFormComment('"+ data.result[i].commentUserName + "', '" + data.result[i].comment + "', " + data.result[i].commentId + ")");
        updateButton.innerHTML = "수정";
        buttonDiv.append(updateButton);

        let deleteButton = document.createElement('button');
        deleteButton.setAttribute('class', 'btn btn-outline-danger');
        deleteButton.setAttribute('onclick', 'deleteComment('+ data.result[i].commentId + ', ' + data.result.boardId + ')');
        deleteButton.innerHTML = "삭제";
        buttonDiv.append(deleteButton);


        let updateDiv = document.createElement('div');
        updateDiv.setAttribute('id', 'updateDiv' + data.result[i].commentId);
        liById.append(updateDiv);
    }
}