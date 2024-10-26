/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encriptar;

/**
 *
 * @author march
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;

public class ventanaRSA extends JFrame implements ActionListener{
    
    private static final long serialVersionUID = 1L;

    
    private JTextField cajaTamPrimo;

        private JButton btnGenenerarClaves;
        private ButtonGroup grupoBotonesOpcion;
        private JButton btnEncriptar, btnDesEncriptar;
        //private JRadioButton btnEncriptar, btnDesEncriptar;
        private JTextArea areaOrigen, areaDestino;
        private Container cntAreas, cntGenClaves;
        private RSA rsa;
        private BigInteger[] textoCifrado;
    
    

    public ventanaRSA() {

            super("Interfaz Grafica - RSA");
            
            getContentPane().setLayout(new BorderLayout(3,3));
            
            cajaTamPrimo = new JTextField();
            cajaTamPrimo.addActionListener(
                    new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    generarClaves();
                }
            });
            
            btnGenenerarClaves = new JButton("Generar Claves");
            btnGenenerarClaves.addActionListener(this);
            
            //btnEncriptar = new JRadioButton("Encriptar", false);
            btnEncriptar = new JButton("Encriptar");
            btnEncriptar.addActionListener( new ManejadorBotonOpcion() );
            btnEncriptar.setEnabled(false);
          //  btnDesEncriptar = new JRadioButton("Desencriptar", false);
            btnDesEncriptar = new JButton("Desencriptar");
            btnDesEncriptar.addActionListener( new ManejadorBotonOpcion() );
            btnDesEncriptar.setEnabled(false);
            
            grupoBotonesOpcion = new ButtonGroup();
            grupoBotonesOpcion.add(btnEncriptar);
            grupoBotonesOpcion.add(btnDesEncriptar);
            
            areaOrigen = new JTextArea();
            areaOrigen.setWrapStyleWord(true);
            areaOrigen.setLineWrap(true);
            areaDestino = new JTextArea();
            areaDestino.setLineWrap(true);
            areaDestino.setEditable(false);
            
            cntAreas = new Container();
            cntGenClaves = new Container();
            
            cntGenClaves.setLayout(new GridLayout(2,3));
            cntGenClaves.add(new JLabel("Digita el tamaño del número primo: "));
            cntGenClaves.add(cajaTamPrimo);
            cntGenClaves.add(btnGenenerarClaves);
            cntGenClaves.add(new JLabel("Digita el texto a cifar/descifrar:"));
            cntGenClaves.add(btnEncriptar);
            cntGenClaves.add(btnDesEncriptar);
            
            cntAreas.setLayout(new GridLayout(1,2,5,5));
            cntAreas.add(new JScrollPane(areaOrigen));
            cntAreas.add(new JScrollPane(areaDestino));
            
            getContentPane().add(cntGenClaves, BorderLayout.NORTH);
            getContentPane().add(cntAreas, BorderLayout.CENTER);
            
            setSize(650,300);
            setVisible(true);
        }

    
    //Veamos ahora el método actionPerformed, que será invocado cada vez que el botón btnGenenerarClaves genere un evento:

        public void actionPerformed( ActionEvent evento ) {
            if(evento.getSource().equals(btnGenenerarClaves))
                generarClaves();
        }
    //Lo único a resaltar aquí es el método getSource de la clase ActionEvent, que en conjunto con el método equals de la clase Object nos permitirá saber quién generó el evento. El siguiente método a tratar es generarClaves, que simplemente invocará los métodos generaPrimos y generaClaves de la clase RSA.java, y nos los mostrará:

    private void generarClaves() {

            if(cajaTamPrimo.getText().equals(""))
                JOptionPane.showMessageDialog(null, 
                       "No haz introducido el tamaño del primo",
                       "Tenemos problemas", JOptionPane.ERROR_MESSAGE);
            else {
                rsa = new RSA(Integer.parseInt(cajaTamPrimo.getText()));
                rsa.generaPrimos();
                rsa.generaClaves();
                JTextArea area = new JTextArea(20,50);
                area.setEditable(false);
                area.setLineWrap(true);
                area.append("Tam clave: "+cajaTamPrimo.getText()+"\n\n");
                area.append("p:["+rsa.damep()+"]\n\nq:["+rsa.dameq()+"]\n\n");
                area.append("Clave publica (n,e):\n\nn:["+
                rsa.damen()+"]\n\ne:["+rsa.damee()+"]\n\n");
                area.append("Clave publica (n,d):\n\nn:["+rsa.damen()+"]\n\nd:["
                +rsa.damed()+"]");
                JOptionPane.showMessageDialog(null, new JScrollPane(area),
            "Primos generados", JOptionPane.INFORMATION_MESSAGE);
                btnEncriptar.setEnabled(true);
                btnDesEncriptar.setEnabled(true);
            }
        }


    /*De aquí podemos tomar algunas cosas interesantes…Comencemos con la clase JOptionPane, la cual nos permitirá usar cuadros de diálogo básicos. En este caso solo usamos su método estático showMessageDialog, en donde podremos introducir un texto a mostrar (o un objeto, como veremos ,más adelante), el título del cuadro de diálogo, y un icono a mostrar. En cuanto al icono, podemos escoger entre imágenes referentes a error, información, alarma, pregunta y creo que no más… por ejemplo, para usar el de error podemos hacer JoptionPane.ERROR_MESSAGE 
    Otro punto interesante de la clase JOptionPane, es que en vez de texto simple podemos colocar un objeto gráfico… por ejemplo podemos colocar un objeto JTextArea como en el código de arriba: JOptionPane.showMessageDialog(null, new JscrollPane(area),”Titulo”) 
    Pasemos entonces a ver cómo está construida la clase interna , que en nuestro caso maneja los eventos de los botones de radio:*/

    private class ManejadorBotonOpcion implements ActionListener {

            
            // manejar eventos de botón de opción
            public void actionPerformed( ActionEvent evento ) {
                if(evento.getSource().equals(btnEncriptar)) {
                    if(areaOrigen.getText().equals(""))
                        JOptionPane.showMessageDialog(null,"No ha introducido datos para cifrar","Tenemos problemas",JOptionPane.ERROR_MESSAGE);
                        else {
                        textoCifrado = rsa.encripta(areaOrigen.getText());
                        areaDestino.setText("");
                        for(int i=0; i<textoCifrado.length; i++)
                            areaDestino.append(textoCifrado[i].toString());
                    }
                } else if(evento.getSource().equals(btnDesEncriptar)) {
                    if(areaOrigen.getText().equals(""))
                        JOptionPane.showMessageDialog(null,"Has introducido datos para cifrar","Tenemos problemas",JOptionPane.ERROR_MESSAGE);
                         else {
                        areaDestino.setText("");
                        String recuperarTextoPlano = rsa.desencripta(textoCifrado);
                        areaDestino.setText(recuperarTextoPlano);
                    }
                }
            }
            
        }
    /*Como puedes ver implementa la interfaz ActionListener, por lo que más abajo tiene que declarar el método actionPerformed. Allí adentro se cifran/decifran lo que se haya puesto en una de las areas de texto. La verdad no hay mucho que explicar aquí así que vamos directamente al código del método main:*/

   
}
