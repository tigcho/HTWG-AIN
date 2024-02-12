clear all
close all

Aufgabe = 'c';

switch Aufgabe
    case 'a'
        % ein erster, einfacher Funktionsplot
        I = [0,2*pi];
        N = 100;
        x = linspace(I(1),I(2),N);
        y = sin(x);
        plot(x,y,'-');
        grid on
    case 'b'
        % Wir vermuten genau eine Polstelle, wissen aber nicht wo die ist
        f = @(x) 1./(x-1);
        I = [0,2];
        N = 100;
        x = linspace(I(1),I(2),N);
        y = f(x);

        Ind = find(abs(y)<=20);
        IndDiff = find(diff(Ind)>1);
        
        plot(x(1:Ind(IndDiff)),y(1:Ind(IndDiff)),'b-');
        grid on
        hold on
        plot(x(Ind(IndDiff+1):end),y(Ind(IndDiff+1):end),'b-');
        
    case 'c'
        % Spiegelung eines Graphen mit sehr schickem plot
        I = [-pi,pi];
        N = 100;
        x = linspace(I(1),I(2),N);
        y = MyFunc(x);
        
        %%
        h = figure(1);
        hold on

        plot(x,y,'-','Color',[0.5,0,0]);
        plot(y,x,'-','Color',[0,0,0.5]);
        plot(x,x,'-','Color',[0,0.5,0]);
        legend('Graph von f','Spiegelung des Graphen','Spiegelachse')

        grid on
        xlabel('x')
        ylabel('y')
        title('Graph und Spiegelung an der Winkelhalbierenden');
        set(gca, 'XAxisLocation', 'origin', 'YAxisLocation', 'origin')
        daspect([1 1 1])
        print(h,'-dpng','Ergebnisbild.png');
end

%%
function y = MyFunc(x)

    y = cos(x).*sin(x);

end

