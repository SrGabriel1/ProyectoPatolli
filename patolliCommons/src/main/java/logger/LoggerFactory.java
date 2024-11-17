/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logger;

/**
 *
 * @author felix
 */
public class LoggerFactory {
    
    public static IPatolliLogger getLogger(Class c) {
        return PatolliLoggerProxy.getInstance(c);
    }
    
}
