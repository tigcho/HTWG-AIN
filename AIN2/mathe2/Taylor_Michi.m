clear all
close all
% Symbolische Variable (muss hin sonst fehler)
syms x
%!!! Entwicklungspunkt (meistens Mitte des Intervalls /hintere Zahl bei "T_f(x,0)" -> x0 = 0) eintragen:
x0 = 0;
%!!! Polynomgrad eintragen (kleine Zahl unten am T - Anzahl der Näherungen):
n = 3;
%!!! Definitionsbereichbreite delta (wie viel von x0 nach links und rechts?):
delta = 3;

% Definitionsbereich I (wird automatisch berechnet):
I = [x0-delta, x0+delta];

sprintf("Berechnung des Taylorpolynoms für den Entwicklungspunkt: x0 = " + x0)
sprintf("Es werden " + 2 + " Näherungen bestimmt.")
sprintf("Der betrachtete Definitionsbereich liegt bei x0 - " + delta + "bis x0 + " + delta)


%% Bestimmen und Ausgeben Taylor-Polynome
sprintf("Bestimmen und Anzeige der Taylor-Polynome")
for k = 0:n
    Tf = simplify(Taylor(@func, x0, k, x));
    disp(['Taylor-Polynom vom Grad ' num2str(k) ': ' char(Tf)]);
end

%% Plotten der n-te der Taylor-Polynom
% Erzeugen von 100 x-Werten im Intervall, um zu plotten:
xWert = linspace(I(1), I(2), 100);

% Plotte die Funktionen im eingeschränkten Bereich mit verschiedenen Farben
figure;
plot(xWert, func(xWert, 0), 'LineWidth', 2, 'Color', 'blue');
hold on;
grid on;
plot(xWert, Taylor(@func, x0, n, xWert), 'LineWidth', 2, 'Color', 'red');

%!!! Schaubild auf Prüfung x-Achse
xlim(I)
%xlim([-5,5])
%!!! Schaubild auf Prüfung y-Achse
ylim([-4,4])

set(gca, 'XAxisLocation', 'origin', 'YAxisLocation', 'origin')
% Legende hinzufügen
legend('Funktion: f', 'Taylorpolynom Tf');
% Anzeigen des Plots
hold off;
%% Berechnen des Taylor-Polynoms

function Tf = Taylor(func, x0, n, x)
    Tf = 0;
    for k = 0:n
        Tf = Tf + func(x0, k)/factorial(k)*(x - x0).^k;
    end
end 

%% zu betrachtende Funktion definieren
function f = func(xx, n)
   syms  x;
   % Funktion eintragen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   ff(x) = log(2-3*x+x.^2);
   dxf = ff;

   for k=1:n
       dxf(x) = diff(dxf(x), x);
   end

   f = dxf(xx);
end