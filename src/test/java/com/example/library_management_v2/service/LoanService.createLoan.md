Vi vill testa LoanService.createLoan() som är hjärtat i vår biblioteksystem.

Vi ett lån händer följande:
- Systemet måste kontrollera att användaren finns
- Systemet måste kontrollera att boken finns
- Systemet måste kontrollera att boken är tillgänglig
- Systemet måste minska antalet tillgängliga exemplar
- Systemet måste skapa ett nytt lån med rätt datum

"Mock-objekt"
Av säkerhetsskäl vill vi inte testa med en verklig databas eftersom datan inuti kan ändras
under vår test och det vill vi inte.
Dessutom är kanske vår databas inte färdig än. Därför är det bra att använda en Mock-objekt.
Mock-objekt ger oss en FEJK-databas som är utmärkt att använda vid test. 

@ExtendWith(MockitoExtension.class) >>> säger åt JUnit att använda Mockito för att hantera våra mock-objekt
@Mock >>> skapar en "fejk" version av repository som vi kan programmera att bete sig som vi vill
@InjectMocks >>> skapar en riktig LoanService men injicerar våra mock-repositories i den

__________

"Förbereda testdata med @BeforeEach"
Nästa steg är att förbereda testdata. Varför gör vi detta i en separat metod?
Tänk dig att du är en kock som ska laga flera olika rätter. 
Istället för att förbereda ingredienserna från början för varje rätt, förbereder du allt en gång 
och använder sedan ingredienserna för olika recept. 
På samma sätt förbereder vi testdata en gång och återanvänder den i olika tester.

@BeforeEach >>> Denna metod körs automatiskt före varje enskilt test. 
Det betyder att varje test börjar med "färska" objekt och inte påverkas av vad som hände i tidigare tester.

För att utföra en Loan behöver vi ha en "User", "Author" och "Book". Vi skapar dessa i vår setUp().
___________

"Arrange-Act-Assert" så kallat Triple A - metoden

Arrange >>> Förbered allt som behövs för test
Act (Agera) >>> Utför den funktion eller handling vi vill testa
Assert (Bekräfta) >>> KOntrollera att resultatet blev som vi ville

Precis som en laboratorium: 
1. Förbereda labbmiljö
2. Utför experiment
3. Analysera resultat

Vi skriver nu en test för när allt går bra, så kallat "happy-path".

Vi behöver skapa ett förväntat lån. 
När vi anropar "loanRepository.save()" i riktig kod och databas, skulle det spara lånet i databasen och
returnera det sparade lånet (med ett genererat ID från databasen). 
Men eftersom vi använder en Mock, måste vi "programmera-definiera" vad mock:en ska returnera när "save()" anropas.

Under vår ACT-del testar vi den metoden vi ville testa:
LoanDTO result = loanService.createLoan(createLoanDTO);

Detta händer när koden körs:
1. loanService.createLoan() anropas med vårt test-DTO
2. Metoden anropar userRepository.findById(1L) >>>>> vår mock returnerar testUser
3. Metoden anropar bookRepository.findById(1L) >>>>> vår mock returnerar testBook
4. Metoden kontrollerar att boken har tillgängliga exemplar (vårt testBook har 5 copies)
5. Metoden minskar tillgängliga exemplar med 1
6. Metoden skapar ett nytt Loan-objekt
7. Metoden anropar loanRepository.save() - vår mock returnerar expectedLoan
8. Metoden konverterar Loan till LoanDTO och returnerar det

Under Assert-delen kontrollerar vi alla delar av metoden/lånet så att den blir som vi ville

Nu när vi skapat en test för denna scenario behöver vi tänka på alla scenarier där det kan gå fel som:
1. Någon försöker låna med user-id som inte finns
2. Någon vill låna en bok som inte finns/existerar
3. Någon försöker låna en bok som är redan slut hos oss
4. Boken som ska lånas ut har bara ett exemplar kvar

Tänk på att vid alla dessa scenarier måste en Exception kastas!

verify(bookRepository, never()).findById(any());
I dessa metoder använder man sig av never() för att säkerställa att systemet slutar att fungera omedelbart. 


