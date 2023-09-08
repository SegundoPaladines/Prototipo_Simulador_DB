import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Simulador_Base_De_Datos  extends Application {

   VBox contenedor_principal = new VBox();
   Tablas tablas = new Tablas();
   
   public void start(Stage stage) {
      //Haciendo El Esenario
      this.getEsenario("vacio");
      Group root = new Group(this.contenedor_principal);
      Scene scene = new Scene(root, 800, 300, Color.BEIGE);
      scene.getStylesheets().add("/css/estilos.css");
      stage.setTitle("Compilador");
      stage.setScene(scene);
      stage.show();
   }
   public void getEsenario(String texto){

      this.contenedor_principal.getChildren().clear();
      this.contenedor_principal.setSpacing(20);
      this.contenedor_principal.setPadding(new Insets(2, 2, 2, 60));
      

      //Campo para meter comandos
      TextArea codigo = new TextArea(texto);
      codigo.setId("editor");

      //Elementos Dentro Del Text Area
      if(texto.equals("vacio")){
         codigo.setText("<Incerta Tu Codigo Aquí>");
      }
      codigo.setPrefColumnCount(15);
      codigo.setPrefHeight(200);
      codigo.setPrefWidth(600);

      //Estructura Del programa
      HBox contenedor_texto = new HBox();
      contenedor_texto.setSpacing(20);
      contenedor_texto.setPadding(new Insets(2, 2, 2, 60));
      contenedor_texto.getChildren().add(codigo);

      Button ejecutar = new Button("Ejecutar");
      ejecutar.setId("ejecutar");

      ejecutar.setOnAction(event -> {
         this.Compilar(codigo.getText().toString().toLowerCase());
      });

      HBox contenedor_boton = new HBox();
      contenedor_boton.setSpacing(20);
      contenedor_boton.setPadding(new Insets(2, 2, 2, 600));
      contenedor_boton.getChildren().add(ejecutar);
      
      this.contenedor_principal.getChildren().addAll(contenedor_texto,contenedor_boton);
     
   }
   public void Compilar(String codigo) {

      this.contenedor_principal.getChildren().clear();
      this.contenedor_principal.setSpacing(20);
      this.contenedor_principal.setPadding(new Insets(2, 2, 2, 60));

      //Validar sintaxys
      Pila validar_parentesis = new Pila();
      Pila validar_corchetes = new Pila();
      Pila validar_llaves = new Pila();
      Pila validar_in = new Pila();
      boolean codigo_valido=true;

      for(int i=0; i<codigo.length(); i++){

         char caracter = codigo.charAt(i);

         if(caracter=='<'){
            validar_in.apilar();
         }
         if(caracter=='>'){
            validar_in.desApilar();
         }

         if(caracter=='('){
            validar_parentesis.apilar();
         }
         if(caracter==')'){
            validar_parentesis.desApilar();
         }
         
         if(caracter=='['){
            validar_corchetes.apilar();
         }
         if(caracter==']'){
            validar_corchetes.desApilar();
         }
         if(caracter=='{'){
            validar_llaves.apilar();
         }
         if(caracter=='}'){
            validar_llaves.desApilar();
         }

      }
      Label aviso = new Label();
      if(validar_in.validarPila()==false){
         aviso.setText("falta abrir o cerrar <>");
         codigo_valido=false;
      }
      if(validar_parentesis.validarPila()==false){
         aviso.setText("falta abrir o cerrar () ");
         codigo_valido=false;
      }
      if(validar_corchetes.validarPila()==false){
         aviso.setText("falta abrir o cerrar  [] ");
         codigo_valido=false;
      }
      if(validar_llaves.validarPila()==false){
         aviso.setText("falta abrir o cerrar {}");
         codigo_valido=false;
      }

      if(codigo.charAt(0)!='<'){
         codigo_valido=false;
         aviso.setText("El codigo debe iniciar con <");
      }
      
      if(codigo_valido==true){

         //obatener comandos y quitar espacios
         String linea_codigo=codigo.toLowerCase();
         char letra='<';
         Cola comandos= new Cola();

         int i=1;
         String comando="";
         while((letra!='{')&&(letra!='[')&&(letra!='(')&&(letra!='>')){
            letra = linea_codigo.charAt(i);
            if((letra!='{')&&(letra!='[')&&(letra!='(')&&(letra!='>')){comando = comando+letra;}
            i++;
         }

         comandos.insertar(comando);

         if((letra=='{')){

            comando="";
            while((letra!='[')&&(letra!='(')&&(letra!='>')){
               letra = linea_codigo.charAt(i);
               if((letra!='{')&&(letra!='[')&&(letra!='(')&&(letra!=')')&&(letra!=']')&&(letra!='}')&&(letra!='>')){comando = comando+letra;}
               i++;
            }
            comandos.insertar(comando);
         }
         if((letra=='[')){
            comando="";
            while((letra!='(')&&(letra!='>')){
               letra = linea_codigo.charAt(i);
               if((letra!='[')&&(letra!='(')&&(letra!=')')&&(letra!=']')&&(letra!='}')&&(letra!='>')){comando = comando+letra;}
               i++;
            }
            comandos.insertar(comando);
         }
         if((letra=='(')){
            comando="";
            while((letra!='>')){
               letra = linea_codigo.charAt(i);
               if((letra!='(')&&(letra!=')')&&(letra!=']')&&(letra!='}')&&(letra!='>')){comando = comando+letra;}
               i++;
            }
            comandos.insertar(comando);
         }

         Nodo aux=comandos.pri;
         String sentencia[]=new String[4];
         i=3;
         while(aux!=null){
            sentencia[i]=aux.codigo;
            aux=aux.siguiente;
            i=i-1;
         }
         for(int j=0; j<4; j++){
            if(sentencia[j]!=null){
               sentencia[j] = sentencia[j].replace("\n", "");
               sentencia[j] = sentencia[j].replaceAll("\\s","");
            }
         }
         int tamano =0;
         int k=0;
         String comandos_validos[]=new String[4];
         while(k<4){
            if(sentencia[k]!=null){
               comandos_validos[tamano]=sentencia[k];
               tamano=tamano+1;
            }
            k=k+1;
         }

         ejecutarCodigo(comandos_validos,tamano,codigo, 1);

      }else{

         Button volver = new Button("Volver");
         volver.setId("ejecutar");

         volver.setOnAction(event -> {
            this.getEsenario(codigo);
         });
         this.contenedor_principal.getChildren().addAll(aviso,volver);

      }

   }
   public void ejecutarCodigo(String[] comandos, int tamano,String codigo, int i) {

      this.contenedor_principal.getChildren().clear();
      this.contenedor_principal.setSpacing(20);
      this.contenedor_principal.setPadding(new Insets(2, 2, 2, 60));

      Label aviso = new Label();

      String texto="";

      Button volver = new Button("Volver");
      volver.setId("ejecutar");

      volver.setOnAction(event -> {
         this.getEsenario(codigo);
      });

      if(tamano>=1){
         String comando1=comandos[0];
         switch(comando1){

            case "creartabla":
                  if(tamano==3){

                     if(!comandos[1].equals("")){

                        String entrada1 = comandos[1];
                        String entrada2 = comandos[2];

                        texto=texto+"Tabla "+entrada1+" Creada Con Exito \n Atributos: "
                        +crearTabla(entrada1,entrada2);

                     }else{
                        texto="Nombre Invalido";
                     }

                  }else{
                     texto="NUMERO DE COMANDOS INCORRECTO";
                  }
                  aviso.setText(texto);
                  this.contenedor_principal.getChildren().addAll(aviso,volver);
               break;

            case "insertarentabla":
                  if(tamano==3){

                     if(!comandos[1].equals("")){

                        String entrada1 = comandos[1];
                        String entrada2 = comandos[2];

                        texto=texto+InsertarEnTabla(entrada1,entrada2);

                     }else{
                        texto="Nombre Invalido";
                     }

                  }else{
                     texto="NUMERO DE COMANDOS INCORRECTO";
                  }
                  aviso.setText(texto);
                  this.contenedor_principal.getChildren().addAll(aviso,volver);
               break;

            case "consultartabla":
                  if(tamano==2){
                     consultarTablaCompleta(comandos[1],codigo);
                  }else if(tamano==3){
                     consultarElemento(comandos[1],comandos[2],codigo);
                  }else{
                     texto="NUMERO DE COMANDOS INCORRECTO";
                     aviso.setText(texto);
                     this.contenedor_principal.getChildren().addAll(aviso,volver);
                  }
               break;

            case "eliminartabla":
                  if(tamano==2){
                     eliminarTabla(comandos[1],codigo);
                  }else if(tamano==3){
                     eliminarElemento(comandos[1],comandos[2],codigo);
                  }else{
                     texto="NUMERO DE COMANDOS INCORRECTO";
                     aviso.setText(texto);
                     this.contenedor_principal.getChildren().addAll(aviso,volver);
                  }
               break;

            default:
               texto="Comando No Reconocido";
               aviso.setText(texto);
               this.contenedor_principal.getChildren().addAll(aviso,volver);
               break;
         }
      }

   }
   public void eliminarElemento(String nombre_tabla, String nombre_atributo, String codigo) {

      this.contenedor_principal.getChildren().clear();
      this.contenedor_principal.setSpacing(20);
      this.contenedor_principal.setPadding(new Insets(2, 2, 2, 60));

      Label aviso = new Label();
      Button volver = new Button("Volver");
      volver.setId("ejecutar");
      volver.setOnAction(event -> {
         this.getEsenario(codigo);
      });
      String texto = "";
      nombre_tabla=nombre_tabla.replaceAll("\\s","");
      if(tablas.tablas.size()>0){
         int pos_tabla=-1;
         for(int i=0; i<tablas.tablas.size(); i++){
            String name = tablas.tablas.get(i).nombre;
            name=name.replaceAll("\\s","");
            if(nombre_tabla.equalsIgnoreCase(name)){
               pos_tabla=i;
            }
         }

         if(pos_tabla!=(-1)){

            if(tablas.tablas.get(pos_tabla).objetos.size()>0){

               GridPane datos = new GridPane();
               datos.setPadding(new Insets(5,4,4,5));
               datos.setGridLinesVisible(true);
               Tabla tabla=tablas.tablas.get(pos_tabla);
               String columnas=tabla.columnas.keySet().toString();
               String atributo="";
               String atributos []= new String[tabla.columnas.size()];
               int cantidad_atributos=0;
               for(int i=1; i<columnas.length()-1;i++){
                  char item = columnas.charAt(i); 
                  if(item==','){
                     atributo=atributo.replaceAll("\\s","");
                     atributos[cantidad_atributos]=atributo;
                     cantidad_atributos=cantidad_atributos+1;
                     atributo="";
                  }
                  if(item!=','){
                     atributo=atributo+item;
                  }
               }
               atributo=atributo.replaceAll("\\s","");
               atributos[cantidad_atributos]=atributo;
               cantidad_atributos=cantidad_atributos+1;

               for(int i=0; i<cantidad_atributos; i++){

                  TextField dato = new TextField(atributos[i]);
                  dato.setEditable(false);
                  dato.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                  dato.prefWidth(100);
                  dato.setId("encabezado");
                  datos.add(dato, i, 0, 1, 1);
               }

               String atr="";
               char a=nombre_atributo.charAt(0);
               int contador=0;
               while(a!='='){
                  a=nombre_atributo.charAt(contador);
                  if(a!='='){
                     atr=atr+a;
                  }
                  contador=contador+1;
               }
               atr=atr.replaceAll("\\s","");
   
               String valor="";
               for(int i=contador; i<nombre_atributo.length(); i++){
                  a=nombre_atributo.charAt(i);
                  if(a!='='){
                     valor=valor+a;
                  }
               }
               valor=valor.replaceAll("\\s","");

               int iteraciones=0;
               Tabla tmp= new Tabla("tmp");
               if(tabla.objetos.size()>0){
                  for(int i=0; i<tablas.tablas.get(pos_tabla).objetos.size();i++){
                     for(int j=0; j<cantidad_atributos; j++){
                        try{
                           String elemento = tabla.objetos.get(i).atributos.get(atributos[j].toString());
                           elemento=elemento.replaceAll("\\s","");
                           String atr_2=atributos[j].toString().replaceAll("\\s","");
                           if(atr.equalsIgnoreCase(atr_2)&&(valor.equalsIgnoreCase(elemento))){
                              tmp.objetos.add(tablas.tablas.get(pos_tabla).objetos.get(i));
                              tablas.tablas.get(pos_tabla).objetos.remove(i);
                              iteraciones=iteraciones+1;
                           }
                        }catch(Exception e){
                           System.out.println("validado");
                        }
                     }
                  }
               }
               if(iteraciones>0){

                  texto="Lista De Elementos Eliminados";
                  aviso.setText(texto);
                  for(int i=0; i<tmp.objetos.size(); i++){
                     for(int j=0; j<cantidad_atributos; j++){

                        String elemento = tmp.objetos.get(i).atributos.get(atributos[j].toString());
                        TextField dato = new TextField(elemento);
                        TextField item = new TextField(elemento);
                        dato.setEditable(false);
                        dato.setId("encabezado");
                        dato.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                        dato.prefWidth(100);
                        datos.add(item,j, iteraciones+1, 1, 1);
                     }
                  }
                  this.contenedor_principal.getChildren().addAll(aviso,datos,volver);
                  
               }else{
                  texto="Elemento No encontrado";
                  aviso.setText(texto);
                  this.contenedor_principal.getChildren().addAll(aviso,volver);
               }

            }else{
               texto="Tabala Vacia";
               aviso.setText(texto);
               this.contenedor_principal.getChildren().addAll(aviso,volver);
            }

         }else{

            texto="Tabala No Encontrada";
            aviso.setText(texto);
            this.contenedor_principal.getChildren().addAll(aviso,volver);

         }
         
      }else{
         texto = "No Hay Tablas: ";
         aviso.setText(texto);
         this.contenedor_principal.getChildren().addAll(aviso,volver);
      }
   }
   public void eliminarTabla(String nombre_tabla, String codigo) {
      this.contenedor_principal.getChildren().clear();
      this.contenedor_principal.setSpacing(20);
      this.contenedor_principal.setPadding(new Insets(2, 2, 2, 60));

      Label aviso = new Label();
      Button volver = new Button("Volver");
      volver.setId("ejecutar");
      volver.setOnAction(event -> {
         this.getEsenario(codigo);
      });
      String texto = "";
      nombre_tabla=nombre_tabla.replaceAll("\\s","");
      if(tablas.tablas.size()>0){

         for(int i=0; i<tablas.tablas.size(); i++){
            String name = tablas.tablas.get(i).nombre;
            name=name.replaceAll("\\s","");
            if(nombre_tabla.equalsIgnoreCase(name)){
               texto=texto+"\n Tabla "+tablas.tablas.get(i).nombre+" En La Posicion "+i+" ha sido eliminada";
               tablas.tablas.remove(i);
            }

         }
         if(texto.equalsIgnoreCase("")){

            texto="Tabala No Encontrada";

         }
         aviso.setText(texto);
         this.contenedor_principal.getChildren().addAll(aviso,volver);
         
      }else{
         texto = "No Hay Tablas: ";
         aviso.setText(texto);
         this.contenedor_principal.getChildren().addAll(aviso,volver);
      }
   }
   public void consultarElemento(String nombre_tabla,String nombre_atributo,String codigo) {
      this.contenedor_principal.getChildren().clear();
      this.contenedor_principal.setSpacing(20);
      this.contenedor_principal.setPadding(new Insets(2, 2, 2, 60));

      GridPane datos = new GridPane();
      datos.setPadding(new Insets(5,4,4,5));
      datos.setGridLinesVisible(true);
      Label aviso = new Label();
      Button volver = new Button("Volver");
      volver.setId("ejecutar");

      volver.setOnAction(event -> {
         this.getEsenario(codigo);
      });
      String texto="";

      if(tablas.tablas.size()>0){

         int pos=-1;
         for(int i=0; i<tablas.tablas.size(); i++){
            if(nombre_tabla.equalsIgnoreCase(tablas.tablas.get(i).nombre)){
               pos=i;
            }
         }
         if(pos!=(-1)){

            Tabla tabla=tablas.tablas.get(pos);
            String columnas=tabla.columnas.keySet().toString();
            String atributo="";
            String atributos []= new String[tabla.columnas.size()];
            int cantidad_atributos=0;
            for(int i=1; i<columnas.length()-1;i++){
               char item = columnas.charAt(i); 
               if(item==','){

                  atributo=atributo.replaceAll("\\s","");
                  atributos[cantidad_atributos]=atributo;
                  cantidad_atributos=cantidad_atributos+1;
                  atributo="";
               }
               if(item!=','){
                  atributo=atributo+item;
               }
            }
            atributo=atributo.replaceAll("\\s","");
            atributos[cantidad_atributos]=atributo;
            cantidad_atributos=cantidad_atributos+1;

            for(int i=0; i<cantidad_atributos; i++){

               TextField dato = new TextField(atributos[i]);
               dato.setEditable(false);
               dato.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
               dato.prefWidth(100);
               dato.setId("encabezado");
               datos.add(dato, i, 0, 1, 1);
            }
            aviso.setText(texto);

            String atr="";
            char a=nombre_atributo.charAt(0);
            int contador=0;
            while(a!='='){
               a=nombre_atributo.charAt(contador);
               if(a!='='){
                  atr=atr+a;
               }
               contador=contador+1;
            }
            atr=atr.replaceAll("\\s","");

            String valor="";
            for(int i=contador; i<nombre_atributo.length(); i++){
               a=nombre_atributo.charAt(i);
               if(a!='='){
                  valor=valor+a;
               }
            }
            valor=valor.replaceAll("\\s","");

            if(tabla.objetos.size()>0){
               int iteraciones=1;
               for(int i=0; i<tabla.objetos.size(); i++){

                  contador=-1;
                  for(int j=0; j<cantidad_atributos; j++){

                     String elemento = tabla.objetos.get(i).atributos.get(atributos[j].toString());
                     elemento=elemento.replaceAll("\\s","");
                     String atr_2=atributos[j].toString().replaceAll("\\s","");
                     if(atr.equalsIgnoreCase(atr_2)&&(valor.equalsIgnoreCase(elemento))){
                        contador=i;
                        iteraciones=iteraciones+1;
                     }
                  }

                  if(contador!=(-1)){
                     for(int j=0; j<cantidad_atributos; j++){

                        String elemento = tabla.objetos.get(contador).atributos.get(atributos[j].toString());
                        TextField dato = new TextField(elemento);
                        TextField item = new TextField(elemento);
                        dato.setEditable(false);
                        dato.setId("encabezado");
                        dato.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                        dato.prefWidth(100);
                        datos.add(item,j, iteraciones, 1, 1);
                     }
                  }

               }
            }

            this.contenedor_principal.getChildren().addAll(aviso,datos,volver);

         }else{
            texto="La Tabla "+nombre_tabla+" No Existe";
            aviso.setText(texto);
            this.contenedor_principal.getChildren().addAll(aviso,volver);
         }

      }else{
         texto="No Hay Tablas: ";
         aviso.setText(texto);
         this.contenedor_principal.getChildren().addAll(aviso,volver);
      }
   }
   public void consultarTablaCompleta(String nombre_tabla, String codigo) {

      this.contenedor_principal.getChildren().clear();
      this.contenedor_principal.setSpacing(20);
      this.contenedor_principal.setPadding(new Insets(2, 2, 2, 60));

      GridPane datos = new GridPane();
      datos.setPadding(new Insets(5,4,4,5));
      datos.setGridLinesVisible(true);
      Label aviso = new Label();
      Button volver = new Button("Volver");
      volver.setId("ejecutar");

      volver.setOnAction(event -> {
         this.getEsenario(codigo);
      });
      String texto="";

      if(tablas.tablas.size()>0){

         int pos=-1;
         for(int i=0; i<tablas.tablas.size(); i++){
            if(nombre_tabla.equalsIgnoreCase(tablas.tablas.get(i).nombre)){
               pos=i;
            }
         }
         if(pos!=(-1)){

            Tabla tabla=tablas.tablas.get(pos);
            String columnas=tabla.columnas.keySet().toString();
            String atributo="";
            String atributos []= new String[tabla.columnas.size()];
            int cantidad_atributos=0;
            for(int i=1; i<columnas.length()-1;i++){
               char item = columnas.charAt(i); 
               if(item==','){

                  atributo=atributo.replaceAll("\\s","");
                  atributos[cantidad_atributos]=atributo;
                  cantidad_atributos=cantidad_atributos+1;
                  atributo="";
               }
               if(item!=','){
                  atributo=atributo+item;
               }
            }
            atributo=atributo.replaceAll("\\s","");
            atributos[cantidad_atributos]=atributo;
            cantidad_atributos=cantidad_atributos+1;

            for(int i=0; i<cantidad_atributos; i++){

               TextField dato = new TextField(atributos[i]);
               dato.setEditable(false);
               dato.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
               dato.prefWidth(100);
               dato.setId("encabezado");
               datos.add(dato, i, 0, 1, 1);
            }
            aviso.setText(texto);

            if(tabla.objetos.size()>0){

               for(int i=0; i<tabla.objetos.size(); i++){

                  for(int j=0; j<cantidad_atributos; j++){

                     String elemento = tabla.objetos.get(i).atributos.get(atributos[j].toString());
                     TextField dato = new TextField(elemento);
                     TextField item = new TextField(elemento);
                     dato.setEditable(false);
                     dato.setId("encabezado");
                     dato.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                     dato.prefWidth(100);
                     datos.add(item,j, i+1, 1, 1);
                  }

               }

            }

            this.contenedor_principal.getChildren().addAll(aviso,datos,volver);

         }else{
            texto="La Tabla "+nombre_tabla+" No Existe";
            aviso.setText(texto);
            this.contenedor_principal.getChildren().addAll(aviso,volver);
         }

      }else{
         texto="No Hay Tablas: ";
         aviso.setText(texto);
         this.contenedor_principal.getChildren().addAll(aviso,volver);
      }
   }
   public String crearTabla(String entrada1, String entrada2) {

      Tabla tabla = new Tabla(entrada1);
      ArrayList <String> atributos = new ArrayList<String>();
      ArrayList <String> tipos = new ArrayList<String>();
                        
      String atributo="";
      for(int j=0; j<entrada2.length(); j++){

       if(entrada2.charAt(j)==','){

         String nombre_atributo="";
          int pos=0;
         for(int k=0; k<atributo.length(); k++){
            if(atributo.charAt(k)=='-'){
                atributos.add(nombre_atributo);
                pos=k;
            }
            if(atributo.charAt(k)!='-'){
               nombre_atributo=nombre_atributo+atributo.charAt(k);
            }
         }
         String tipo_atributo="";
         for(int k=pos+1; k<atributo.length(); k++){
            tipo_atributo=tipo_atributo+atributo.charAt(k);
         }
         tipos.add(tipo_atributo);

         atributo="";
       }
         if(entrada2.charAt(j)!=','){

               atributo=atributo+entrada2.charAt(j);

         }
      }

      if(atributo!=""){

      String nombre_atributo="";
      int pos=0;
      for(int k=0; k<atributo.length(); k++){
        if(atributo.charAt(k)=='-'){
           atributos.add(nombre_atributo);
           pos=k;
        }
       if(atributo.charAt(k)!='-'){
            nombre_atributo=nombre_atributo+atributo.charAt(k);
       }
      }
      String tipo_atributo="";
         for(int k=pos+1; k<atributo.length(); k++){
            tipo_atributo=tipo_atributo+atributo.charAt(k);
         }
            tipos.add(tipo_atributo);

      }
      String texto="";
      for(int k=0; k<atributos.size(); k++){

         texto=texto+"\n "+atributos.get(k) +" - "+tipos.get(k);

      }

      for(int i=0; i<atributos.size(); i++){

         tabla.columnas.put(atributos.get(i), tipos.get(i));

      }
      tablas.tablas.add(tabla);
      return texto;
   }
   public String InsertarEnTabla(String entrada1, String entrada2) {
      
      String texto="Insertado en"+entrada1+"Objeto con Atributos: ";
      int tab=-1;
      for(int i=0; i<tablas.tablas.size(); i++){
         if(entrada1.equalsIgnoreCase(tablas.tablas.get(i).nombre)){tab=i;}
      }
      if(tab!=(-1)){

         Objeto objeto = new Objeto(entrada1);
         ArrayList <String> atributos = new ArrayList<String>();
         ArrayList <String> valores = new ArrayList<String>();
                           
         String atributo="";
         for(int j=0; j<entrada2.length(); j++){
   
          if(entrada2.charAt(j)==','){
   
            String nombre_atributo="";
             int pos=0;
            for(int k=0; k<atributo.length(); k++){
               if(atributo.charAt(k)=='='){
                   atributos.add(nombre_atributo);
                   pos=k;
               }
               if(atributo.charAt(k)!='='){
                  nombre_atributo=nombre_atributo+atributo.charAt(k);
               }
            }
            String valor_atributo="";
            for(int k=pos+1; k<atributo.length(); k++){
               valor_atributo=valor_atributo+atributo.charAt(k);
            }
            valores.add(valor_atributo);
   
            atributo="";
          }
            if(entrada2.charAt(j)!=','){
   
                  atributo=atributo+entrada2.charAt(j);
   
            }
         }
   
         if(atributo!=""){
   
         String nombre_atributo="";
         int pos=0;
         for(int k=0; k<atributo.length(); k++){
           if(atributo.charAt(k)=='='){
              atributos.add(nombre_atributo);
              pos=k;
           }
          if(atributo.charAt(k)!='='){
               nombre_atributo=nombre_atributo+atributo.charAt(k);
          }
         }
         String valor_atributo="";
            for(int k=pos+1; k<atributo.length(); k++){
               valor_atributo=valor_atributo+atributo.charAt(k);
            }
               valores.add(valor_atributo);
   
         }
         
         for(int k=0; k<atributos.size(); k++){
   
            texto=texto+"\n "+atributos.get(k) +" = "+valores.get(k);
   
         }
   
         for(int i=0; i<atributos.size(); i++){
   
            objeto.atributos.put(atributos.get(i), valores.get(i));
   
         }

         if(objeto.atributos.size()==tablas.tablas.get(tab).columnas.size()){

            tablas.tablas.get(tab).objetos.add(objeto);

         }else{texto="El numero de atributos del objeto no corresponde con los de\n Tabla: "+entrada1;}

      }else{texto="La Tabla "+entrada1+" No existe";}

      return texto;
   }

   public static void main(String args[]){
      launch(args);
   }
}

