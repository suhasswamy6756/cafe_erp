🔹 Phase 1: Core Tenant / Auth System

foundation for all modules

Order	Table	Purpose
1	companies	Tenant (café brand/company)
2	users	Login users (employees/admins)
3	roles	Role master
4	user_roles	Mapping user ↔ role
5	permissions	Permission master
6	role_permissions	Mapping role ↔ permission
7	user_refresh_tokens	JWT refresh tokens
8	user_sessions	Active sessions
9	users_audit_logs	Audit log table for users module

✅ Once this is done → Authentication, RBAC, and multi-tenant foundation is ready.

🔹 Phase 2: Catalogue / Menu System
Order	Table	Purpose
1	categories	Item grouping
2	items	Menu items/raw materials
3	item_images	Item images
4	modifier_groups	Extra toppings/options group
5	modifier_options	Individual modifiers
6	item_modifier_groups	Item ↔ modifier group link
7	discounts / item_discounts	Discounts
8	catalogue_audit_logs	For changes tracking
🔹 Phase 3: Inventory / Outlets
Order	Table	Purpose
1	locations	Café outlets (belongs to a company)
2	stocks	Inventory by outlet
3	stock_adjustments	Adjust stock entries
4	stock_change_logs	Quantity history
5	suppliers	Vendors
6	purchase / purchase_items	Purchase Orders
7	receipts / receipt_items	Goods received
8	transfers / transfer_items	Inter-outlet transfers
9	inventory_audit_logs	Audit for stock changes
🔹 Phase 4: Recipes / Production
Order	Table	Purpose
1	recipes	Recipe master
2	recipe_items	Ingredients used
3	batches	Batch tracking
4	production_costs	Material/Labor/Overhead costs
5	intermediates / intermediate_stock	Intermediate products
🔹 Phase 5: Support Tables
Table	Purpose
uoms	Units of Measurement
tax_modes / taxes / tax_applicable_modes	Tax definitions
charges / charge_modes / charge_applicable_modes	Extra charges
institutes (if used)	External references