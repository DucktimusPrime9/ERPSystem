# ERPSystem
Enterprise Resource Planning application with support for countries and regions

DesignAn enterprise resource planning application that will handle: (in US [5 Regions], 3 countries, and center around the consumer (electric toothbrush manufacturing – quip)

1.
----------------------------------------------------------
-	HR (standard/basic functionality – design for reusability)
The design pattern being used for the HR application is the Singleton pattern. Because there will never be a need for the console to show more than one menu at the same time, the Singleton pattern will prevent any duplicate instances which could not even be used by the end user. This prevents resource waste and prevents more than one object from being used.

To compile this application, you must compile using the erpsystem package. You can use the following two commands to compile and then subsequently run the application.

javac erpsystem/*.java

java erpsystem/HRApp

------------------------------------------------------------

2.	 Add 
-	Accounts Payable/Receivable (standard/basic functionality)
-	*Implement the solution (Include a singleton design)
------------------------------------------------------------
3.	Add
-	Marketing (standard/basic functionality)
-	*Implement the solution (include an abstract factory design)
------------------------------------------------------------
