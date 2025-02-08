package raisetech.StudentManagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

  private static final Logger logger = LoggerFactory.getLogger((GlobalExceptionHandler.class));

  //不正な引数エラー（例：IDが0以下などのプログラム上のエラー）
 @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex){
   logger.warn("不正な引数エラー: {}", ex.getMessage());
   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("エラー: " + ex.getMessage());
  }

  // JSONファーマットエラー（例：文字列を数値としてバースできない場合）
  @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleJsonParseException(org.springframework.http.converter.HttpMessageNotReadableException ex){
    logger.warn("JSONフォーマットエラー", ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("リクエストの形式が正しくありません。");
  }

// そのたの予期しないエラー（例：NullPointerException））
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex){
    logger.error("予期しないエラーが発生しました", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーエラーが発生しました。詳細: " + ex.getMessage());
  }
}