clear all
close all

global P

P=(linspace(-1,3,5))';
P(:,2)=[5; 7; 10; 9; 3]-4; 

c0 = [1 1 1];
[c res] = lsqnonlin(@Residuum,c0);

fprintf("\n\nError = %.2e\n\n",sqrt(res));

%%
h = figure(1);
plot(P(:,1),P(:,2),'ro');
grid on
xx = linspace(P(1,1),P(end,1),100);
hold on
plot(xx,Ansatzfunction(xx,c),'k-')
legend('data','ansatzfunction')
set(gca, 'XAxisLocation', 'origin', 'YAxisLocation', 'origin')
print(h,'-dpng','Ergebnisbild_MyReg.png');

%%
function y=Residuum(c)
global P

y = P(:,2)-Ansatzfunction(P(:,1),c);

end

function y=Ansatzfunction(x,c)

%y = c(1)+c(2)*x;
y = c(1)+c(2)*x+c(3)*x.^2;
%y = c(1)+c(2)*x+c(3)*x.^2+c(4)*x.^3;

%y = c(1)*exp(-(x-c(2)).^2/c(3));
 
end