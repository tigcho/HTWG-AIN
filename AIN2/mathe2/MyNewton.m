clear all
close all

% die Funktion, deren Nullstelle gesucht ist
f  = @(x) x-cos(x);
df = @(x) 1+sin(x);

%%
TOL   = 1.e-08;
ItMax = 1000;
it    = 0;
% Startwert
x = 0;
fprintf("%d-Iterations: x = %.2f, |f(x)| = %.2e\n",it,x,abs(f(x)));

while abs(f(x))>TOL
    it = it+1;
    % Newton-Iteration
    x = x - f(x)/df(x);
    % Ausgabe der Näherungswerte
    fprintf("%d-Iterations: x = %.2f, |f(x)| = %.2e\n",it,x,abs(f(x)));
    % Abbruchkriterium für den Fall, dass das Verfahren nicht konvergiert
    if it>ItMax
        fprintf("never ending story\n");
        break;
    end
end