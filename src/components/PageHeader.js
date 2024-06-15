import React from 'react'
import { Layout, Row, Col, Button } from 'antd'
import Login from './Login'
import Register from './Register'
const { Header } = Layout
function PageHeader({ loggedIn, signoutOnClick, signinOnSuccess }) {
	return (
		<Header>
			<Row justify='space-between'>
				<Col>{'Favorites'}</Col>
				<Col>
					{(loggedIn && (
						<Button shape='round' onClick={signoutOnClick}>
							Logout
						</Button>
					)) || (
						<>
							<Login onSuccess={signinOnSuccess} />
							<Register />
						</>
					)}
				</Col>
			</Row>
		</Header>
	)
}
export default PageHeader
