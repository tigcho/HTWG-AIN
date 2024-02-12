clear all
close all

Q = load("GlockeR.dat");

I = [min(Q(:,1)),max(Q(:,1))];
x = Q(:,1);
f = Q(:,2);
N = length(f);
h = (I(2)-I(1))/(N-1);
LOOP = 100;
delta = 1.e-04;

%%
forg = f;
for l=1:LOOP
    d2f = Diff(f,h,2);
    f   = f + delta*d2f;
end

%%
fig = figure(1);
plot(x,forg,'ko')
hold on
grid on
plot(x,f,'ro-')
xlabel('x')
ylabel('y')
set(gca, 'XAxisLocation', 'origin', 'YAxisLocation', 'origin')
legend('f orig','f smooth')
print(fig,'-dpng','Ergebnisbild_Glaettung.png');

%%
function df = Diff(f,h,order)

N = length(f);

switch order
    case 1
        % gemittelter Differenzenquotient
        D=diag(ones(1,N-1),1)-diag(ones(1,N-1),-1);
        % Vorwärtsdifferenzenquotient am linken Rand
        D(1,1)=-2;
        D(1,2)=2;
        % Rückwärtsdifferenzenquotient am rechten Rand
        D(N,N)=2;
        D(N,N-1)=-2;

        D = D/2/h;
    case 2
        D=-2*diag(ones(1,N))+diag(ones(1,N-1),-1)+diag(ones(1,N-1),1);
        D(1,:)=zeros(1,N);
        D(N,:)=zeros(1,N);
 
        D = D/h/h;
    otherwise
        df=f;
end

df = D*f;

end

