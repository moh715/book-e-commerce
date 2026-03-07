# book-e-commerce
# Online Bookstore System
The Online Bookstore System is a web-based platform that allows customers to browse, purchase, and track books online. The system connects three main actors — customers, administrators, and a payment gateway each with distinct responsibilities and interactions within the platform.
Customers must register and log in before they can place orders. Once logged in, they can search and browse available books, manage a shopping cart, proceed through checkout, and track their orders after purchase. They can also leave reviews on books they have bought.
Administrators are responsible for maintaining the system's content and operations. This includes managing the book inventory, handling orders, and overseeing user accounts. Administrators do not interact with the storefront directly but manage the platform from the backend.
Payment processing is handled by an external payment gateway, which is invoked during checkout to validate and complete financial transactions. The gateway operates as a secondary actor, supporting the purchase flow without initiating any actions independently.
The system also includes a book recommendation feature. Based on a customer's purchase history and the reviews they have submitted, the system generates a list of suggested books when the customer browses the catalogue. This feature is built into the browsing flow and does not require any additional input from the customer the recommendations are produced automatically using the data already stored in the system.

# Use Case Diagram
The Use Case Diagram captures the functional requirements of the Online Bookstore System by mapping each actor to the use cases they interact with.
Customers interact with the largest set of use cases. They register into the system, which includes logging in, then can browse books, add items to their cart, and check out. Checkout includes making a payment, which in turn triggers the payment gateway's process payment use case. Customers can also track their orders and leave reviews, where leaving a review extends the track order use case.
Administrators interact with a separate set of use cases focused on platform management. After logging in, they can manage inventory, manage orders, and manage user accounts. These use cases are independent of the customer-facing functionality.
The Payment Gateway is involved only through the process payment use case, which is included by the make payment use case initiated during customer checkout.

# Activity Diagram: Buy a Book
The Buy a Book activity diagram models the control flow a customer goes through when purchasing a book, from browsing to order confirmation.
The activity begins with the customer browsing or searching for books. Upon viewing a book's details, the customer decides whether to add it to their cart. If they choose to do so, the system checks whether the customer is already logged in. If not, the customer is directed to log in or register before the item is added. Once authenticated, the book is added to the cart.
From the cart, the customer either continues shopping, looping back to the browse activity, or proceeds to checkout. At checkout, the customer enters a shipping address and selects a payment method, then provides payment details. The system validates the payment information. If validation fails, an error is shown and the customer is prompted to re-enter their details. If validation succeeds, the order is processed, a confirmation email is sent, and the customer is able to track their order. The activity then reaches its final state.



