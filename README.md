# 1. Cel projektu

Celem projektu jest stworzenie gry Saper w języku Java z graficznym interfejsem użytkownika (GUI). Gra będzie używać programowania obiektowego, w tym dziedziczenia. Interfejs zostanie wykonany przy użyciu biblioteki Swing. Pozwala ona tworzyć okna, przyciski i obsługiwać zdarzenia.

Projekt będzie składał się z czterech plików Java. Każdy z nich będzie odpowiadał za określoną część programu.

# 2. Opis działania gry

Po włączeniu programu pojawi się okno z planszą z przyciskami. Niektóre pola będą miały ukryte miny. Gracz będzie odkrywał pola, klikając przycisk.

- Jeśli gracz odkryje pole z miną, przegrywa.
- Jeśli pole nie ma miny, pojawi się liczba min na sąsiednich polach.
Gracz wygrywa, gdy odkryje wszystkie pola, które nie mają min.
Po zakończeniu gry pojawi się komunikat o wygranej lub przegranej.

Interfejs będzie też miał przycisk, który pozwoli rozpocząć nową grę.

3. Podział projektu na cztery pliki

1. "Pole.java" to klasa podstawowa.

Klasa będzie przedstawiać jedno pole planszy.

Odpowiedzialności:

- przechowywanie informacji o odkryciu pola,
- przechowywanie liczby sąsiednich min,
- określanie, czy pole zawiera minę,
- udostępnianie metod do odkrywania pola.

To będzie klasa bazowa dla klasy "PoleMina".

# 2. "PoleMina.java", klasa dziedzicząca

Klasa będzie przedstawiać pole, które zawiera minę. Będzie dziedziczyć po klasie "Pole".

Odpowiedzialności:

- reprezentowanie pola z miną,
- nadpisywanie metody sprawdzającej zawartość pola,
- umożliwienie rozpoznania miny po kliknięciu pola w GUI.

Dziedziczenie pozwoli użyć wspólnych właściwości klasy "Pole" bez potrzeby ich ponownego definiowania.

# 3. "Plansza.java", logika gry i GUI

Klasa będzie zajmować się planszą i jej graficznym wyglądem.

Odpowiedzialności:

- tworzenie okna gry i siatki przycisków,
- rozmieszczanie min na planszy,
- obliczanie liczby sąsiednich min,
- obsługa kliknięć gracza,
- aktualizowanie wyglądu odkrytych pól,
- sprawdzanie warunków wygranej i przegranej,
- dodanie przycisku nowej gry.

Do stworzenia interfejsu zostaną użyte elementy biblioteki Swing, takie jak "JFrame", "JPanel", "JButton" i "JOptionPane".

# 4. "Gra.java", klasa główna

Klasa uruchomi całą aplikację.

Odpowiedzialności:

- zawieranie metody "main()",
- uruchamianie interfejsu graficznego,
- tworzenie obiektu klasy "Plansza",
- rozpoczęcie rozgrywki.

# 5. Wykorzystanie dziedziczenia

Dziedziczenie będzie użyte między klasami "Pole" i "PoleMina". Klasa "PoleMina" rozszerzy klasę "Pole" przez słowo kluczowe "extends".

Na przykład klasa "Pole" może mieć metodę "czyJestMina()", która informuje, czy dane pole ma minę. Klasa "PoleMina" nadpisze tę metodę. Dzięki temu zawsze zwraca wartość "true".

Dzięki temu program może traktować różne rodzaje pól w podobny sposób. Jednocześnie rozpoznaje pola, które zawierają miny.

# 6. Technologie

W projekcie zostaną wykorzystane:

- Java,
- Java Swing do stworzenia GUI,
- dziedziczenie i nadpisywanie metod,
- tablice dwuwymiarowe,
- obsługa zdarzeń i kliknięć przycisków,
- generator liczb losowych do rozmieszczania min.

# 7. Podsumowanie

Projekt będzie graficzną wersją gry Saper. Gracz będzie sterować rozgrywką za pomocą myszy. Podział na cztery pliki pozwoli zachować porządek w kodzie. Dziedziczenie umożliwi praktyczne wykorzystanie zasad programowania obiektowego.

Aplikacja będzie mieć planszę z ukrytymi minami. Pokaże liczbę sąsiednich min. Wyświetli komunikaty o zakończeniu gry. Pozwoli też zacząć nową rozgrywkę.
