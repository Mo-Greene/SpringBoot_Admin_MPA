var main = {
    init : function () {
        const _this = this;

        // 게시글 등록
        $('#btn-save').on('click', function () {
            _this.save();
        });

        // 게시글 수정
        $('#btn-modify').on('click', function () {
            _this.modify();
        })

        // 게시글 삭제
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    /**
     * 게시글 등록
     */
    save : function () {
        const data = {
            category: $('#urlCategory').val(),
            categoryBoard: $('#categoryBoard').val(),
            boardTitle: $('#boardTitle').val(),
            boardContent: $('#boardContent').val()
        };

        $.ajax({
            type: 'POST',
            url: '/' + data.category + '/write',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (res) {
                console.log(res)
                alert('게시글 등록')
                window.location.href = '/' + data.category
            },
            error: function (data) {
                console.log(data)
            },
        })
    },
    /**
     * 게시글 수정
     */
    modify : function () {
        const data = {
            categoryBoard: $('#categoryBoard').val(),
            boardTitle: $('#boardTitle').val(),
            boardContent: $('#boardContent').val()
        };

        const category = $('#urlCategory').val();
        const boardNo = $('#boardNo').val();

        $.ajax({
            type: 'PUT',
            url: '/' + category + '/modify/' + boardNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (res) {
                console.log(res)
                alert('게시글 수정')
                window.location.href = '/' + category
            },
            error: function (data) {
                console.log(data)
                alert('게시글 수정 실패')
            },
        })
    },
    /**
     * 게시글 삭제
     */
    delete: function () {
        const boardNo = $('#boardNo').val();
        const category = $('#urlCategory').val();


        $.ajax({
            type: 'DELETE',
            url: '/' + category + '/delete/' + boardNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function (res) {
                console.log(res)
                alert('게시글 삭제');
                window.location.href = '/' + category
            },
            //todo ??error 가 정상적으로 실행된다;;
            error: function () {
                alert('게시글 삭제실패')
                window.location.href = '/' + category
            }
        })
    },
}

main.init();
