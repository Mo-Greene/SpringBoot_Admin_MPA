## 1차 목표 자유게시판

todo
```
<insert id="insertBoardFree" parameterType="BoardFree">
  INSERT INTO base_board (title, content, views, created_at, modified_at, author)
  VALUES (#{title}, #{content}, #{views}, #{createdAt}, #{modifiedAt}, #{author});
  INSERT INTO board_free (id, specific_column_1, specific_column_2, specific_column_3)
  VALUES (LAST_INSERT_ID(), #{specificColumn1}, #{specificColumn2}, #{specificColumn3});
</insert>
```
