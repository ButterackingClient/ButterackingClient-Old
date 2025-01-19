/*     */ package net.optifine.expr;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class ExpressionParser
/*     */ {
/*     */   private IExpressionResolver expressionResolver;
/*     */   
/*     */   public ExpressionParser(IExpressionResolver expressionResolver) {
/*  18 */     this.expressionResolver = expressionResolver;
/*     */   }
/*     */   
/*     */   public IExpressionFloat parseFloat(String str) throws ParseException {
/*  22 */     IExpression iexpression = parse(str);
/*     */     
/*  24 */     if (!(iexpression instanceof IExpressionFloat)) {
/*  25 */       throw new ParseException("Not a float expression: " + iexpression.getExpressionType());
/*     */     }
/*  27 */     return (IExpressionFloat)iexpression;
/*     */   }
/*     */ 
/*     */   
/*     */   public IExpressionBool parseBool(String str) throws ParseException {
/*  32 */     IExpression iexpression = parse(str);
/*     */     
/*  34 */     if (!(iexpression instanceof IExpressionBool)) {
/*  35 */       throw new ParseException("Not a boolean expression: " + iexpression.getExpressionType());
/*     */     }
/*  37 */     return (IExpressionBool)iexpression;
/*     */   }
/*     */ 
/*     */   
/*     */   public IExpression parse(String str) throws ParseException {
/*     */     try {
/*  43 */       Token[] atoken = TokenParser.parse(str);
/*     */       
/*  45 */       if (atoken == null) {
/*  46 */         return null;
/*     */       }
/*  48 */       Deque<Token> deque = new ArrayDeque<>(Arrays.asList(atoken));
/*  49 */       return parseInfix(deque);
/*     */     }
/*  51 */     catch (IOException ioexception) {
/*  52 */       throw new ParseException(ioexception.getMessage(), ioexception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private IExpression parseInfix(Deque<Token> deque) throws ParseException {
/*  57 */     if (deque.isEmpty()) {
/*  58 */       return null;
/*     */     }
/*  60 */     List<IExpression> list = new LinkedList<>();
/*  61 */     List<Token> list1 = new LinkedList<>();
/*  62 */     IExpression iexpression = parseExpression(deque);
/*  63 */     checkNull(iexpression, "Missing expression");
/*  64 */     list.add(iexpression);
/*     */     
/*     */     while (true) {
/*  67 */       Token token = deque.poll();
/*     */       
/*  69 */       if (token == null) {
/*  70 */         return makeInfix(list, list1);
/*     */       }
/*     */       
/*  73 */       if (token.getType() != TokenType.OPERATOR) {
/*  74 */         throw new ParseException("Invalid operator: " + token);
/*     */       }
/*     */       
/*  77 */       IExpression iexpression1 = parseExpression(deque);
/*  78 */       checkNull(iexpression1, "Missing expression");
/*  79 */       list1.add(token);
/*  80 */       list.add(iexpression1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IExpression makeInfix(List<IExpression> listExpr, List<Token> listOper) throws ParseException {
/*  86 */     List<FunctionType> list = new LinkedList<>();
/*     */     
/*  88 */     for (Token token : listOper) {
/*  89 */       FunctionType functiontype = FunctionType.parse(token.getText());
/*  90 */       checkNull(functiontype, "Invalid operator: " + token);
/*  91 */       list.add(functiontype);
/*     */     } 
/*     */     
/*  94 */     return makeInfixFunc(listExpr, list);
/*     */   }
/*     */   
/*     */   private IExpression makeInfixFunc(List<IExpression> listExpr, List<FunctionType> listFunc) throws ParseException {
/*  98 */     if (listExpr.size() != listFunc.size() + 1)
/*  99 */       throw new ParseException("Invalid infix expression, expressions: " + listExpr.size() + ", operators: " + listFunc.size()); 
/* 100 */     if (listExpr.size() == 1) {
/* 101 */       return listExpr.get(0);
/*     */     }
/* 103 */     int i = Integer.MAX_VALUE;
/* 104 */     int j = Integer.MIN_VALUE;
/*     */     
/* 106 */     for (FunctionType functiontype : listFunc) {
/* 107 */       i = Math.min(functiontype.getPrecedence(), i);
/* 108 */       j = Math.max(functiontype.getPrecedence(), j);
/*     */     } 
/*     */     
/* 111 */     if (j >= i && j - i <= 10) {
/* 112 */       for (int k = j; k >= i; k--) {
/* 113 */         mergeOperators(listExpr, listFunc, k);
/*     */       }
/*     */       
/* 116 */       if (listExpr.size() == 1 && listFunc.size() == 0) {
/* 117 */         return listExpr.get(0);
/*     */       }
/* 119 */       throw new ParseException("Error merging operators, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
/*     */     } 
/*     */     
/* 122 */     throw new ParseException("Invalid infix precedence, min: " + i + ", max: " + j);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeOperators(List<IExpression> listExpr, List<FunctionType> listFuncs, int precedence) throws ParseException {
/* 128 */     for (int i = 0; i < listFuncs.size(); i++) {
/* 129 */       FunctionType functiontype = listFuncs.get(i);
/*     */       
/* 131 */       if (functiontype.getPrecedence() == precedence) {
/* 132 */         listFuncs.remove(i);
/* 133 */         IExpression iexpression = listExpr.remove(i);
/* 134 */         IExpression iexpression1 = listExpr.remove(i);
/* 135 */         IExpression iexpression2 = makeFunction(functiontype, new IExpression[] { iexpression, iexpression1 });
/* 136 */         listExpr.add(i, iexpression2);
/* 137 */         i--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private IExpression parseExpression(Deque<Token> deque) throws ParseException {
/*     */     FunctionType functiontype, functiontype1;
/* 143 */     Token token = deque.poll();
/* 144 */     checkNull(token, "Missing expression");
/*     */     
/* 146 */     switch (token.getType()) {
/*     */       case NUMBER:
/* 148 */         return makeConstantFloat(token);
/*     */       
/*     */       case IDENTIFIER:
/* 151 */         functiontype = getFunctionType(token, deque);
/*     */         
/* 153 */         if (functiontype != null) {
/* 154 */           return makeFunction(functiontype, deque);
/*     */         }
/*     */         
/* 157 */         return makeVariable(token);
/*     */       
/*     */       case BRACKET_OPEN:
/* 160 */         return makeBracketed(token, deque);
/*     */       
/*     */       case OPERATOR:
/* 163 */         functiontype1 = FunctionType.parse(token.getText());
/* 164 */         checkNull(functiontype1, "Invalid operator: " + token);
/*     */         
/* 166 */         if (functiontype1 == FunctionType.PLUS)
/* 167 */           return parseExpression(deque); 
/* 168 */         if (functiontype1 == FunctionType.MINUS) {
/* 169 */           IExpression iexpression1 = parseExpression(deque);
/* 170 */           return makeFunction(FunctionType.NEG, new IExpression[] { iexpression1 });
/* 171 */         }  if (functiontype1 == FunctionType.NOT) {
/* 172 */           IExpression iexpression = parseExpression(deque);
/* 173 */           return makeFunction(FunctionType.NOT, new IExpression[] { iexpression });
/*     */         } 
/*     */         break;
/*     */     } 
/* 177 */     throw new ParseException("Invalid expression: " + token);
/*     */   }
/*     */ 
/*     */   
/*     */   private static IExpression makeConstantFloat(Token token) throws ParseException {
/* 182 */     float f = Config.parseFloat(token.getText(), Float.NaN);
/*     */     
/* 184 */     if (f == Float.NaN) {
/* 185 */       throw new ParseException("Invalid float value: " + token);
/*     */     }
/* 187 */     return new ConstantFloat(f);
/*     */   }
/*     */ 
/*     */   
/*     */   private FunctionType getFunctionType(Token token, Deque<Token> deque) throws ParseException {
/* 192 */     Token tokenPeek = deque.peek();
/*     */     
/* 194 */     if (tokenPeek != null && tokenPeek.getType() == TokenType.BRACKET_OPEN) {
/* 195 */       FunctionType functiontype1 = FunctionType.parse(token.getText());
/* 196 */       checkNull(functiontype1, "Unknown function: " + token);
/* 197 */       return functiontype1;
/*     */     } 
/* 199 */     FunctionType functiontype = FunctionType.parse(token.getText());
/*     */     
/* 201 */     if (functiontype == null)
/* 202 */       return null; 
/* 203 */     if (functiontype.getParameterCount(new IExpression[0]) > 0) {
/* 204 */       throw new ParseException("Missing arguments: " + functiontype);
/*     */     }
/* 206 */     return functiontype;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeFunction(FunctionType type, Deque<Token> deque) throws ParseException {
/* 212 */     if (type.getParameterCount(new IExpression[0]) == 0) {
/* 213 */       Token token = deque.peek();
/*     */       
/* 215 */       if (token == null || token.getType() != TokenType.BRACKET_OPEN) {
/* 216 */         return makeFunction(type, new IExpression[0]);
/*     */       }
/*     */     } 
/*     */     
/* 220 */     Token token1 = deque.poll();
/* 221 */     Deque<Token> deque1 = getGroup(deque, TokenType.BRACKET_CLOSE, true);
/* 222 */     IExpression[] aiexpression = parseExpressions(deque1);
/* 223 */     return makeFunction(type, aiexpression);
/*     */   }
/*     */   
/*     */   private IExpression[] parseExpressions(Deque<Token> deque) throws ParseException {
/* 227 */     List<IExpression> list = new ArrayList<>();
/*     */     
/*     */     while (true) {
/* 230 */       Deque<Token> deque1 = getGroup(deque, TokenType.COMMA, false);
/* 231 */       IExpression iexpression = parseInfix(deque1);
/*     */       
/* 233 */       if (iexpression == null) {
/* 234 */         IExpression[] aiexpression = list.<IExpression>toArray(new IExpression[list.size()]);
/* 235 */         return aiexpression;
/*     */       } 
/*     */       
/* 238 */       list.add(iexpression);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IExpression makeFunction(FunctionType type, IExpression[] args) throws ParseException {
/* 243 */     ExpressionType[] aexpressiontype = type.getParameterTypes(args);
/*     */     
/* 245 */     if (args.length != aexpressiontype.length) {
/* 246 */       throw new ParseException("Invalid number of arguments, function: \"" + type.getName() + "\", count arguments: " + args.length + ", should be: " + aexpressiontype.length);
/*     */     }
/* 248 */     for (int i = 0; i < args.length; i++) {
/* 249 */       IExpression iexpression = args[i];
/* 250 */       ExpressionType expressiontype = iexpression.getExpressionType();
/* 251 */       ExpressionType expressiontype1 = aexpressiontype[i];
/*     */       
/* 253 */       if (expressiontype != expressiontype1) {
/* 254 */         throw new ParseException("Invalid argument type, function: \"" + type.getName() + "\", index: " + i + ", type: " + expressiontype + ", should be: " + expressiontype1);
/*     */       }
/*     */     } 
/*     */     
/* 258 */     if (type.getExpressionType() == ExpressionType.FLOAT)
/* 259 */       return new FunctionFloat(type, args); 
/* 260 */     if (type.getExpressionType() == ExpressionType.BOOL)
/* 261 */       return new FunctionBool(type, args); 
/* 262 */     if (type.getExpressionType() == ExpressionType.FLOAT_ARRAY) {
/* 263 */       return new FunctionFloatArray(type, args);
/*     */     }
/* 265 */     throw new ParseException("Unknown function type: " + type.getExpressionType() + ", function: " + type.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeVariable(Token token) throws ParseException {
/* 271 */     if (this.expressionResolver == null) {
/* 272 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/* 274 */     IExpression iexpression = this.expressionResolver.getExpression(token.getText());
/*     */     
/* 276 */     if (iexpression == null) {
/* 277 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/* 279 */     return iexpression;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeBracketed(Token token, Deque<Token> deque) throws ParseException {
/* 285 */     Deque<Token> deque1 = getGroup(deque, TokenType.BRACKET_CLOSE, true);
/* 286 */     return parseInfix(deque1);
/*     */   }
/*     */   
/*     */   private static Deque<Token> getGroup(Deque<Token> deque, TokenType tokenTypeEnd, boolean tokenEndRequired) throws ParseException {
/* 290 */     Deque<Token> deque2 = new ArrayDeque<>();
/* 291 */     int i = 0;
/* 292 */     Iterator<Token> iterator = deque.iterator();
/*     */     
/* 294 */     while (iterator.hasNext()) {
/* 295 */       Token token = iterator.next();
/* 296 */       iterator.remove();
/*     */       
/* 298 */       if (i == 0 && token.getType() == tokenTypeEnd) {
/* 299 */         return deque2;
/*     */       }
/*     */       
/* 302 */       deque2.add(token);
/*     */       
/* 304 */       if (token.getType() == TokenType.BRACKET_OPEN) {
/* 305 */         i++;
/*     */       }
/*     */       
/* 308 */       if (token.getType() == TokenType.BRACKET_CLOSE) {
/* 309 */         i--;
/*     */       }
/*     */     } 
/*     */     
/* 313 */     if (tokenEndRequired) {
/* 314 */       throw new ParseException("Missing end token: " + tokenTypeEnd);
/*     */     }
/* 316 */     return deque2;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String message) throws ParseException {
/* 321 */     if (obj == null)
/* 322 */       throw new ParseException(message); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\ExpressionParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */