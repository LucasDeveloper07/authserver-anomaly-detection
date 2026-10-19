# Authorization Server com detectação de anomalias

Servidor de autenticação/autorização que registra eventos de login,
detecta comportamento suspeito por IP e bloqueia requisições ou
suspende usuários.

## Scripts SQL (Tabelas)

``` SQL
CREATE TABLE roles(
	id UUID NOT NULL PRIMARY KEY,
	name VARCHAR(30) NOT NULL UNIQUE
); -- tabela para as roles

CREATE TABLE users(
	id UUID NOT NULL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(150) NOT NULL UNIQUE,
	password VARCHAR(300) NOT NULL,
	situation VARCHAR(30) NOT NULL,
	created_at TIMESTAMPTZ,
	updated_at TIMESTAMPTZ,

	CONSTRAINT chk_situation CHECK (situation in ('ACTIVE', 'SUSPENDED'))
); -- tabela de users com a coluna situation para controlar o acesso da conta 

CREATE TABLE user_roles(
	user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	role_id UUID NOT NULL REFERENCES roles(id),

	PRIMARY KEY(user_id, role_id)
); -- tabela para o relacionamento dos users com suas roles

CREATE TABLE auth_events (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id) ON DELETE SET NULL,
    attempted_email VARCHAR(150),
    ip_address INET NOT NULL,
    user_agent VARCHAR(500),
    event_type VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT chk_event_type CHECK (event_type IN ('LOGIN_SUCCESS', 'LOGIN_FAILED', 'REQUEST_BLOCKED', 'USER_SUSPENDED'))
); -- tabela para registro dos eventos de autenticação

CREATE TABLE blocked_ips(
	ip_address INET PRIMARY KEY,
	blocked_at TIMESTAMPTZ NOT NULL,
	expires_at TIMESTAMPTZ
); -- tabela para controle dos ips bloqueados
```