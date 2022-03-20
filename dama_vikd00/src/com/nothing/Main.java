package com.nothing;
import java.util.Scanner;

//David Vikor 2022      vikd00@vse.cz

public class Main {

    public static class Sachovnica {
        public void vykresliSachovnicu(int[][] stavPlochy) {
            boolean vypln;
            boolean kamen;

            //Vykresli oznacenie stlpcov
            System.out.println(vypisOznacenieStlpcov());

            //For loop prechadzajuca cez jednotlive riadky:
            for (int i = 0; i < 8; i++) {
                //Vykresli predelovu ciaru
                System.out.println(horizontalnaCiara());

                //Riadok sa vykresluje do dvoch cmd lines
                String prvyRiadok = (8 - i) + " |";
                String druhyRiadok = "  |";

                //For loop prechadzajuca stlpce v riadku
                for (int j = 0; j < 8; j++) {
                    //Pozri sa ci na danom mieste je kamen
                    kamen = stavPlochy[i][j] == 0 ? false : true;

                    //Rozhodenie sachovnice zabezpecuje delitelnost dvomi
                    if (i % 2 == 0) {
                        if (j % 2 == 0) {
                            vypln = false;
                        } else {
                            vypln = true;
                        }
                    } else {
                        if (j % 2 != 0) {
                            vypln = false;
                        } else {
                            vypln = true;
                        }
                    }

                    if (vypln && kamen) {
                        prvyRiadok += "#/‾‾\\#|";
                        druhyRiadok += "#\\__/" + stavPlochy[i][j] + "|";
                    } else if (vypln && !kamen) {
                        prvyRiadok += "######|";
                        druhyRiadok += "######|";
                    } else if (!vypln && kamen) {
                        prvyRiadok += " /‾‾\\ |";
                        druhyRiadok += " \\__/" + stavPlochy[i][j] + "|";
                    } else {
                        prvyRiadok += "      |";
                        druhyRiadok += "      |";
                    }
                }
                System.out.println(prvyRiadok + " " + (8 - i));
                System.out.println(druhyRiadok);
            }
            //Vykresli predelovu ciaru
            System.out.println(horizontalnaCiara());
            //Vykresli oznacenie stlpcov
            System.out.println(vypisOznacenieStlpcov());
        }

        private String horizontalnaCiara() {
            //Odsadenie ciary => odsadenie tabulky kvoli oznaceniu riadkov
            String ciara = "  ";

            // Ciara ma 57 pismen pretoze sirka stvorca je 6 krat "-" a ma 9 krat predel "+" => 6*8+9=57
            for (int i = 0; i < 57; i++) {
                //Po kazdych 7 chars vykresli "+" -> pretoze indexujeme od 0, posuvame z 8 na 7
                if (i % 7 == 0) {
                    ciara += "+";
                } else {
                    ciara += "-";
                }
            }

            //Vrat ciaru
            return ciara;
        }

        private String vypisOznacenieStlpcov() {
            //Naformatovane oznacenie stlpcov
            return "     A      B      C      D      E      F      G      H";
        }
    }

    static class Dama {
        Sachovnica sachovnica;
        int[][] stavSachovnice;
        int[] vybranyKamen;
        int hracNaTahu;
        int scoreHrac1;
        int scoreHrac2;
        Scanner scanner;
        String[] vstupUzivatela;

        public Dama() {
            this.sachovnica = new Sachovnica();
            this.stavSachovnice = new int[][]{
                    {0, 2, 0, 2, 0, 2, 0, 2},
                    {2, 0, 2, 0, 2, 0, 2, 0},
                    {0, 2, 0, 2, 0, 2, 0, 2},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 1, 0, 1, 0, 1, 0},
                    {0, 1, 0, 1, 0, 1, 0, 1},
                    {1, 0, 1, 0, 1, 0, 1, 0},
            };
            this.scanner = new Scanner(System.in);
            this.scoreHrac1 = 0;
            this.scoreHrac2 = 0;
            this.hracNaTahu = 1;
        }

        //Funkcia ktora spusti hru
        public void spustiHru() {
            while (kolo()) ;
        }

        //Funkcia zabezpecujuca individualne kolo hry
        public boolean kolo() {
            int vybraneX, vybraneY;
            int posunX, posunY;
            //Vykresli sachovnicu
            this.sachovnica.vykresliSachovnicu(this.stavSachovnice);
            //Vykresli instrukcie
            System.out.println();
            System.out.println("    <== Na tahu HRAC " + hracNaTahu + " ==>");
            System.out.println("Zadajte suradnice x a y (napr: A 3) kamena ktory chcete posunut, pozor na format, vstupy nie su dokonale osetrene: ");
            //Sformatuj input
            this.vstupUzivatela = this.scanner.nextLine().split(" ");

            vybraneX = pismenoNaCislo(this.vstupUzivatela[0].charAt(0));
            vybraneY = 8 - Integer.valueOf(this.vstupUzivatela[1]);

            //Ak bol input proti pravidlam, skusaj dokedy nebude korektny
            while (!vyberKamen(vybraneX, vybraneY)) {
                //Vypis instrukcie
                System.out.println("Neplatny tah, zadajte prosim znovu suradnice...");
                System.out.println("Zadajte suradnice x a y (napr: A 3) kamena ktory chcete posunut, pozor na format, vstupy nie su dokonale osetrene:: ");
                this.vstupUzivatela = this.scanner.nextLine().split(" ");
                vybraneX = pismenoNaCislo(this.vstupUzivatela[0].charAt(0));
                vybraneY = 8 - Integer.valueOf(this.vstupUzivatela[1]);
            };

            //Vypis instrukcie
            System.out.println("Zadajte suradnice x a y (napr: B 4) kam chcete kamen posunut, pozor na format, vstupy nie su dokonale osetrene:: ");
            this.vstupUzivatela = this.scanner.nextLine().split(" ");
            posunX = pismenoNaCislo(this.vstupUzivatela[0].charAt(0));
            posunY = 8 - Integer.valueOf(this.vstupUzivatela[1]);

            //Ak bol input proti pravidlam, skusaj dokedy nebude korektny
            while (!posunKamen(posunX, posunY)) {
                //Vypis instrukcie
                System.out.println("Neplatny tah, zadajte prosim znovu suradnice...");
                System.out.println("Zadajte suradnice x a y (napr: B 4) kam chcete kamen posunut, pozor na format, vstupy nie su dokonale osetrene: ");
                this.vstupUzivatela = this.scanner.nextLine().split(" ");
                posunX = pismenoNaCislo(this.vstupUzivatela[0].charAt(0));
                posunY = 8 - Integer.valueOf(this.vstupUzivatela[1]);
            }


            //Vypis skore
            if (this.scoreHrac1 > 12) {
                System.out.println("    <== Vyhral HRAC 1 ==> ");
                return false;
            } else if (this.scoreHrac2 > 12) {
                System.out.println("    <== Vyhral HRAC 2 ==> ");
                return false;
            } else {
                System.out.println("Aktualne skore:");
                System.out.println("HRAC 1: " + this.scoreHrac1);
                System.out.println("HRAC 2: " + this.scoreHrac2);
            }

            zmenaHraca();
            return true;
        }

        //Funkcia meniaca hraca na tahu
        public void zmenaHraca() {
            if (this.hracNaTahu == 1) {
                this.hracNaTahu = 2;
            } else {
                this.hracNaTahu = 1;
            }
        }


        //Funkcia sluziaca na vyber kamena na sachovnici -> overi ci patri hracovi na tahu
        public boolean vyberKamen(int y, int x) {
            //Skontroluj ci nie je mimo sachovnice
            if (x < 0 || x > 7 || y < 0 || y > 7) {
                return false;
            }


            //Skontroluj ci na danom mieste je kamen a ci patri hracovi na tahu
            if (this.hracNaTahu == this.stavSachovnice[x][y]) {
                this.vybranyKamen = new int[]{x, y};
                return true;
            }

            return false;
        }

        public boolean posunKamen(int y, int x) {
            System.out.println("x " + x + "  y "+ y);
            //Skontroluj ci nie je mimo sachovnice
            if (x < 0 || x > 7 || y < 0 || y > 7) {
                return false;
            }

            if (hracNaTahu == 1) {
                //Mozme ist len do predu
                if (this.vybranyKamen[0] - 1 == x) {
                    //Vpravo po ciernych polickach
                    if (this.vybranyKamen[1] + 1 == y) {
                        //Ak je na danom poli kamen hraca jeden -> neplatny tah
                        if (this.stavSachovnice[x][y] == 1) {
                            return false;
                            //Ak je na danom poli kamen druheho hraca -> pokusime sa ho preskocit
                        } else if (this.stavSachovnice[x][y] == 2) {
                            if (x - 1 > 0 && x - 1 < 8 && y + 1 > 0 && y + 1 < 8) {
                                if (this.stavSachovnice[x - 1][y + 1] == 0) {
                                    this.stavSachovnice[vybranyKamen[0]][vybranyKamen[1]] = 0;
                                    this.stavSachovnice[x][y] = 0;
                                    this.stavSachovnice[x - 1][y + 1] = 1;
                                    this.scoreHrac1++;
                                    return true;
                                }
                            }
                        } else {
                            this.stavSachovnice[vybranyKamen[0]][vybranyKamen[1]] = 0;
                            this.stavSachovnice[x][y] = 1;
                            return true;
                        }
                    }
                    //Vlavo po ciernych polickach
                    else if (this.vybranyKamen[1] - 1 == y) {
                        //Ak je na danom poli kamen hraca jeden -> neplatny tah
                        if (this.stavSachovnice[x][y] == 1) {
                            return false;
                            //Ak je na danom poli kamen druheho hraca -> pokusime sa ho preskocit
                        } else if (this.stavSachovnice[x][y] == 2) {
                            if (x - 1 > 0 && x - 1 < 8 && y - 1 > 0 && y + 1 < 8) {
                                if (this.stavSachovnice[x - 1][y - 1] == 0) {
                                    this.stavSachovnice[vybranyKamen[0]][vybranyKamen[1]] = 0;
                                    this.stavSachovnice[x][y] = 0;
                                    this.stavSachovnice[x - 1][y - 1] = 1;
                                    this.scoreHrac1++;
                                    return true;
                                }
                            }
                        } else {
                            this.stavSachovnice[vybranyKamen[0]][vybranyKamen[1]] = 0;
                            this.stavSachovnice[x][y] = 1;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
            //Mozme ist len do zadu
            else {
                //Mozme ist len do zadu
                if (this.vybranyKamen[0] + 1 == x) {
                    //Vpravo po ciernych polickach
                    if (this.vybranyKamen[1] + 1 == y) {
                        //Ak je na danom poli kamen hraca dva -> neplatny tah
                        if (this.stavSachovnice[x][y] == 2) {
                            return false;
                            //Ak je na danom poli kamen druheho hraca -> pokusime sa ho preskocit
                        } else if (this.stavSachovnice[x][y] == 1) {
                            if (x + 1 > 0 && x + 1 < 8 && y + 1 > 0 && y + 1 < 8) {
                                if (this.stavSachovnice[x + 1][y + 1] == 0) {
                                    this.stavSachovnice[vybranyKamen[0]][vybranyKamen[1]] = 0;
                                    this.stavSachovnice[x][y] = 0;
                                    this.stavSachovnice[x + 1][y + 1] = 2;
                                    this.scoreHrac2++;
                                    return true;
                                }
                            }
                        } else {
                            this.stavSachovnice[vybranyKamen[0]][vybranyKamen[1]] = 0;
                            this.stavSachovnice[x][y] = 2;
                            return true;
                        }
                    }
                    //Vlavo po ciernych polickach
                    else if (this.vybranyKamen[1] - 1 == y) {
                        //Ak je na danom poli kamen hraca dva -> neplatny tah
                        if (this.stavSachovnice[x][y] == 2) {
                            System.out.println("1");
                            return false;
                            //Ak je na danom poli kamen druheho hraca -> pokusime sa ho preskocit
                        } else if (this.stavSachovnice[x][y] == 1) {
                            System.out.println("2");
                            if (x + 1 > 0 && x + 1 < 8 && y - 1 > 0 && y - 1 < 8) {
                                if (this.stavSachovnice[x + 1][y - 1] == 0) {
                                    this.stavSachovnice[vybranyKamen[0]][vybranyKamen[1]] = 0;
                                    this.stavSachovnice[x][y] = 0;
                                    this.stavSachovnice[x + 1][y - 1] = 2;
                                    this.scoreHrac2++;
                                    return true;
                                }
                            }
                        } else {
                            this.stavSachovnice[vybranyKamen[0]][vybranyKamen[1]] = 0;
                            this.stavSachovnice[x][y] = 2;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
            return false;
        }
    }

    //Funkcia meniaca pismeno z inputu na cislo
    private static int pismenoNaCislo(char pismeno) {
        switch (pismeno) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            default:
                return -1;
        }
    }


    public static void main(String[] args) {
        //Uvodne info
        System.out.println("\n David Vikor 2022 \nZjednodusena dama: kamen sa moze pohybovat o jedno policko po diagonale vpravo a vlavo,\n" +
                "ak chce hrac preskocit kamen supera, zada aktualne suradnice kamena ktory chce preskocit, nie suradnice za nepriatelskym kamenom :-)");

        //Novy objekt damy
        Dama hraDamy = new Dama();
        //Spustime damu
        hraDamy.spustiHru();
    }
}
