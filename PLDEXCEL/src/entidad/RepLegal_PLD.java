/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;


public class RepLegal_PLD {
    int Id_siseg;
    String Nombre;
    EstatusUsuario estatus; 
    int Estatus;
    String Email;
    
   public RepLegal_PLD() {
   }
    

    public RepLegal_PLD(int Id_siseg, String Nombre, EstatusUsuario estatus, String Email){
            
            this.Id_siseg = Id_siseg;
            this.Nombre = Nombre;
            this.estatus = estatus;
            this.Email = Email;
    }

	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public int getId_siseg() {
		return Id_siseg;
	}

	public void setId_siseg(int id_siseg) {
		Id_siseg = id_siseg;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

    public EstatusUsuario getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusUsuario estatus) {
        this.estatus = estatus;
    }

    
}
