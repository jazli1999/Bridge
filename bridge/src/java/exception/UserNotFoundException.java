/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bridge.exception;

/**
 * @date    2019-9-4 10:24:19
 * @author  luoe
 */
public class UserNotFoundException extends Exception{
    private static final long serialVersionUID = 8712363279947073702L;
    
    public UserNotFoundException(){
        
    }
    
    public UserNotFoundException(String msg){
        super(msg);
    }

}
