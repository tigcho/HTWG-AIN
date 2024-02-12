clear all
close all

Aufgabe = 3;
nn = 1:100;

%%
switch Aufgabe
    
    case 1
        %%
        an = (1+1./nn).^nn;
%        an = (1+1./nn).^(nn+1);
    case 2
        %% Test: alpha=0.1, 20'000<nn<24'000
        alpha = 0.1;
        an = nn.^alpha./log(nn);
        %% Test: beta=10, 1<nn<20
 %       beta = 10;
 %       an = nn.^beta./exp(nn);
    case 3
        %% 
        gn = cos(nn)./nn;
        gn(2,:) = sin(nn)./nn;
    case 4
        %% F_n = F_{n-1}+F_{n-2}, F_1=F_2=1, nn<=10
        Fn(1) = 1; Fn(2) = 1;
        for i=3:nn(end)
            Fn(i) = Fn(i-1)+Fn(i-2);
        end
        an = Fn(1:end-1)./Fn(2:end);
        clear nn;
        nn = 1:length(an);
    case 5
        %% Heron: a = x^(1/k)
        k = 8; x = 5; an(1) = 1000;
        for i=2:nn(end)
            an(i) = 1/k*((k-1)*an(i-1)+x/an(i-1)^(k-1));
        end
    case 6
        lambda = 4;
        an(1) = 1/2;
        for i=2:nn(end)
            an(i) = lambda*an(i-1)*(1-an(i-1));
        end
end


%%
if Aufgabe==3 
    plot(gn(1,:),gn(2,:),'ko-','MarkerFaceColor',[0,0.5,0]);
    grid on
    daspect([1 1 1]);
    xlabel('g_n^1')
    ylabel('g_n^2')
else
    plot(nn,an,'ko','MarkerFaceColor',[1,0,0]);
    grid on
    xlabel('n')
    ylabel('a_n')
end