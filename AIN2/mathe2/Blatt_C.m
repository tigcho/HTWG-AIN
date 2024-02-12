clear all
close all

syms a

% Das Volumen
V(a)   = (16-2*a)*(10-2*a)*a;
% erste Ableitung, deren Nullst. gesucht ist
dV(a)  = simplify(diff(V(a),a));
% krititsche Punkte 
kP     = solve(dV(a)==0,a);
% an welchen krit. Pkten hat V negative
% zweite Ableitung
d2V(a) = simplify(diff(dV(a),a));
Ind    = find(d2V(kP)<0);

fprintf("Das Volumen ist maximal für ");
fprintf("a=%.2f und beträgt V=%.2f\n",kP(Ind),V(kP(Ind)));

%%
h = figure(1);
hold on
I=[0,8];
aa = linspace(I(1),I(2),1000);
plot(aa,V(aa),'k-');
plot(aa,dV(aa),'color',[0.7,0.7,0.7]);
plot(kP(Ind),V(kP(Ind)),'ro');
grid on
set(gca, 'XAxisLocation', 'origin', 'YAxisLocation', 'origin')
xlabel('Einschnittsgröße a');
ylabel('Volumen V')
legend('V(a)','dV(a)','Maximum')
print(h,'-dpng','Ergebnisbild_Blatt_C.png');
