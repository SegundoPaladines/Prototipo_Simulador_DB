public class Cola {	
     
    public Nodo pri;
    public Nodo ult; 
    
    public Cola () {
        this.pri  =null;
        this.ult = null;
    }
    public void insertar(String codigo) {
    	
        Nodo nuevo= new Nodo();
        nuevo.codigo=codigo;
        nuevo.siguiente = null;

        if (this.pri==null)
        {            
            this.pri = nuevo;
            this.ult = this.pri;
        }
        else
        {
            nuevo.siguiente = this.pri;
            this.pri = nuevo;
            
        }
    }
    public void vaciarCola() {
        if (this.pri!=null)
       	{        
	        this.pri = null;
            this.ult = null;         
	    }    
        
    }   
}