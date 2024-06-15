import React from 'react'
import { Layout, Row, Col, Button } from 'antd'
import Login from './Login'
import Register from './Register'
import Favorites from './Favorites'
const { Header } = Layout
function PageHeader({ loggedIn, signoutOnClick, signinOnSuccess, favoriteItems }) {
	return (
		<Header>
			<Row justify='space-between'>
				<Col>{loggedIn && <Favorites favoriteItems={favoriteItems} />}</Col>
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
