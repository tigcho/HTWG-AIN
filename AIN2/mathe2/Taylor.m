clear all
close all
% 1. Intervall, Entwicklungspunkt und Polynomsgrad(Ordnung) eintragen
% 2. Ableitungen über Konsole mit diff(f(x), x,...) machen und in
%    switch-case eintragen 

%% Variablen definition
% Zu betrachtenden Intervall eingeben
Ix = [-5,0.9];
Iy = [-2,4];
% Polynomsgrad
N = 1;
% Entwiscklungspunkt (meist mittig des Intervallbereiches)
x0 = 0;

x = linspace(Ix(1),Ix(2),100);


%% Taylorpolynom
Tf = 0;
for k=0: N
    Tf = Tf + DfuncSym(x0,k)/factorial(k)*(x-x0).^k;
end


%% Ploten der Funktionen f(x) und fN(x)
plot(x,Dfunc(x,0),'k-');
grid on;
hold on;
plot(x0,Dfunc(x0,0),'ro');
plot(x,Tf,'r-');
xlim(Ix);
ylim(Iy);
set(gca,'XAxisLocation', 'origin', 'YAxisLocation', 'origin');

%% Ableitunegn der funktion f(x)
function y = Dfunc(x,k)

switch k
    % Grundfunktion f(x)
    case 0
        y = log(2 - 3*x + x.^2);
    % Erste Ableitung
    case 1
        y = (2*x - 3)./(x.^2 - 3*x + 2);
    % Zweite Ableitung
    case 2
        y = 2./(x.^2 - 3*x + 2) - (2*x - 3).^2./(x.^2 - 3*x + 2).^2;
    % Dritte Ableitung
    case 3
        y = (2*(2*x - 3).^3)./(x.^2 - 3*x + 2).^3 - (8*x - 12)./(x.^2 - 3*x + 2).^2 - (2*(2*x - 3))./(x.^2 - 3*x + 2).^2;
end
end

%% Für sehr große Polynomsgrade ohne das man alle Ableitungen rechnen muss
function y = DfuncSym(x,k)
syms s

f(s) = log(2-3*s+s.^2);
for i = 1 : k
    f(s) = diff(f(s), s);
end

y = subs(f(s), s=x);

end